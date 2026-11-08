import Task1.BankStructure.*;
import Task2.Documents.*;
import Task3.Merchant;
import Task3.MerchantFactory;
import Task3.Operation;
import common.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
public final class RegressionTests {
    private static int checks;
    private static void check(boolean condition, String message) {
        checks++; if (!condition) throw new AssertionError(message);
    }
    private static void money(BigDecimal actual, String expected) {
        check(actual.compareTo(new BigDecimal(expected)) == 0, actual + " != " + expected);
    }
    private static void fails(Class<? extends Throwable> type, Runnable operation) {
        checks++;
        try { operation.run(); } catch (Throwable ex) {
            if (type.isInstance(ex)) return;
            throw new AssertionError("Unexpected exception", ex);
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }
    public static void main(String[] args) {

        Department mini = new Department("Mini").add(new CashDesk("C1",new BigDecimal("0.10"),2));
        Department region = new Department("R").add(new Department("Branch").add(mini))
            .add(new CashDesk("C2",new BigDecimal("0.20"),3));
        Department bank = new Department("Bank").add(region).add(new Department("Empty"));
        money(bank.cash(),"0.30"); check(bank.operations()==5,"Recursive totals");
        money(mini.cash(),"0.10"); check(mini.operations()==2,"Leaf subtree totals");
        fails(IllegalArgumentException.class, () -> mini.add(bank));
        fails(IllegalArgumentException.class, () -> bank.add(bank));
        fails(IllegalArgumentException.class, () -> bank.add(region));
        for (Delivery delivery : List.of(new Print(),new Pdf(),new Signed(),new Diia())) {
            for (Document doc : List.of(new Receipt("P7",delivery), new PaymentOrder("P7",delivery),
                    new AccountStatement("UA7",delivery))) check(doc.issue().contains("7"),"Every combination works");
        }
        Receipt receipt = new Receipt("P7",new Print());
        check(receipt.issue().contains("Друк"),"Initial delivery");
        receipt.setDelivery(new Pdf()); check(receipt.issue().contains("PDF"),"Runtime bridge switch");
        MerchantFactory factory = new MerchantFactory(); List<Operation> history = new ArrayList<>();
        for (int i=0;i<50000;i++) {
            int id=i%20;
            Merchant m=factory.get("M"+id,"Shop"+id,"logo","food","5411","address"+id);
            history.add(new Operation(BigDecimal.ONE,LocalDate.of(2026,11,5),"UA7",m));
        }
        check(history.size()==50000,"50k operations"); check(factory.count()==20,"20 actual merchants");
        check(history.get(0).merchant()==history.get(20).merchant(),"Shared identity");
        check(history.get(0).merchant()!=history.get(1).merchant(),"Distinct merchants");
        fails(IllegalArgumentException.class, () -> factory.get("M0","Changed","logo","food","5411","address0"));
        Logger.log("Tests", "PASS: " + checks + " checks");
    }
}
