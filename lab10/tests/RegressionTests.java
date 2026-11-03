import Task1.*;
import Task2.*;
import Task3.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Перевірки інваріантів без сторонніх бібліотек. */
public final class RegressionTests {
    private static int checks;
    private static void check(boolean condition) {
        checks++;
        if (!condition) throw new AssertionError("Check " + checks + " failed");
    }
    private static void rejects(Runnable action, String expected) {
        try { action.run(); } catch (IllegalStateException | IllegalArgumentException e) {
            check(e.getMessage().contains(expected)); return;
        }
        throw new AssertionError("Expected rejection: " + expected);
    }
    private static LoanApplication.Builder valid() {
        return new LoanApplication.Builder().client("Клієнт").amount(new BigDecimal("300000")).termMonths(36);
    }
    public static void main(String[] args) throws Exception {
        rejects(() -> valid().client(" ").build(), "не вказано клієнта");
        rejects(() -> valid().amount(null).build(), "не вказано суму кредиту");
        rejects(() -> valid().amount(BigDecimal.ZERO).build(), "більшою за нуль");
        rejects(() -> valid().amount(new BigDecimal("-1")).build(), "більшою за нуль");
        rejects(() -> valid().termMonths(0).build(), "строк");
        rejects(() -> valid().termMonths(-1).build(), "строк");
        LoanApplication.Builder builder = valid();
        LoanApplication built = builder.build();
        builder.client("Інший").amount(BigDecimal.ONE).termMonths(1);
        check(built.getClient().equals("Клієнт") && built.getAmount().intValueExact() == 300000 && built.getTermMonths() == 36);
        check(new LoanDirector().consumerLoan("Клієнт").getCollateral().equals("немає"));
        check(new LoanDirector().carLoan("Клієнт").isInsured());
        for (BankingProductFactory factory : List.of(new RetailFactory(), new PremiumFactory(), new BusinessFactory(), new StudentFactory())) {
            BankingPackage products = BankingPackage.from(factory);
            check(products.getSegment().equals(products.getCard().segment()));
            check(products.getSegment().equals(products.getAccount().segment()));
            check(products.getSegment().equals(products.getTariff().segment()));
        }
        rejects(() -> BankingPackage.from(new BankingProductFactory() {
            public Segment segment() { return BusinessFactory.SEGMENT; }
            public Card createCard() { return new BusinessCard(); }
            public CurrentAccount createAccount() { return new BusinessCurrentAccount(); }
            public TariffPlan createTariff() { return new RetailTariffPlan(); }
        }), "різних сегментів");
        LocalDate from = LocalDate.of(2026, 10, 1), to = LocalDate.of(2026, 10, 31);
        List<Transaction> transactions = List.of(
            new Transaction(from.minusDays(1), "OUTSIDE", new BigDecimal("999")),
            new Transaction(from, "A & <B>, \"C\"", new BigDecimal("10.10")),
            new Transaction(to, "Expense", new BigDecimal("-3.05")),
            new Transaction(to.plusDays(1), "OUTSIDE", new BigDecimal("999")));
        final StatementData[] captured = new StatementData[1];
        StatementGenerator probe = new StatementGenerator() {
            protected StatementDocument createDocument(StatementData data) { captured[0] = data; return new AuditDocument(data); }
        };
        probe.generate(transactions, from, to, "Тест");
        check(captured[0].transactions().size() == 2);
        check(captured[0].income().compareTo(new BigDecimal("10.10")) == 0);
        check(captured[0].expense().compareTo(new BigDecimal("3.05")) == 0);
        check(captured[0].net().compareTo(new BigDecimal("7.05")) == 0);
        rejects(() -> probe.generate(transactions, to, from, "Тест"), "період");
        rejects(() -> probe.generate(transactions, from, to, " "), "адресата");
        String[] formats = {"PDF", "XML", "CSV", "AUDIT-TEXT"};
        List<StatementGenerator> generators = List.of(new ClientStatementGenerator(), new TaxStatementGenerator(),
            new AccountingStatementGenerator(), new AuditorStatementGenerator());
        for (int i = 0; i < generators.size(); i++) {
            StatementDocument document = generators.get(i).generate(transactions, from, to, "Тест");
            check(document.format().equals(formats[i]));
            check(!document.render().contains("OUTSIDE"));
        }
        String xml = new XmlDocument(captured[0]).render();
        var parser = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();
        var parsed = parser.parse(new java.io.ByteArrayInputStream(xml.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        check(parsed.getElementsByTagName("transaction").item(0).getTextContent().equals("A & <B>, \"C\""));
        check(new CsvDocument(captured[0]).render().contains("\"A & <B>, \"\"C\"\"\""));
        probe.generate(List.of(), from, to, "Тест");
        check(captured[0].transactions().isEmpty() && captured[0].net().signum() == 0);
        System.out.println("OK: " + checks + " checks");
    }
}
