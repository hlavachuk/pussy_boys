package Task4;

import common.Logger;

public final class AccountOpening {
    private AccountOpening() {}
    public static final class Identification {
        public void verify(String client) { Logger.log("Identification", "Ідентифіковано " + client); }
    }
    public static final class Sanctions {
        public boolean allowed(String client) {
            Logger.log("Sanctions", "Перевірено санкційні списки: " + client);
            return !client.equals("BLOCKED");
        }
    }
    public static final class Accounts {
        private int sequence;
        public String create(String client) {
            String id = "UA" + (++sequence);
            Logger.log("Accounts", "Створено " + id + " для " + client); return id;
        }
    }
    public static final class Cards {
        public void issue(String account) { Logger.log("Cards", "Випуск/перевипуск картки " + account); }
    }
    public static final class OnlineBanking {
        public void connect(String account) { Logger.log("OnlineBanking", "Підключено " + account); }
    }
    public static final class Contracts {
        public void send(String account) { Logger.log("Contracts", "Відправлено договір " + account); }
    }
    public static final class OpeningFacade {
        private final Identification identification;
        private final Sanctions sanctions;
        private final Accounts accounts;
        private final Cards cards;
        private final OnlineBanking online;
        private final Contracts contracts;
        public OpeningFacade(Identification identification, Sanctions sanctions, Accounts accounts,
                             Cards cards, OnlineBanking online, Contracts contracts) {
            this.identification = identification; this.sanctions = sanctions; this.accounts = accounts;
            this.cards = cards; this.online = online; this.contracts = contracts;
        }
        public String open(String client) {
            Logger.log("OpeningFacade", "Початок відкриття: " + client);
            identification.verify(client);
            if (!sanctions.allowed(client)) throw new SecurityException("Клієнт у санкційному списку");
            String account = accounts.create(client);
            cards.issue(account); online.connect(account); contracts.send(account);
            return account;
        }
    }
    public static final class Channel {
        private final String name;
        private final OpeningFacade facade;
        public Channel(String name, OpeningFacade facade) { this.name = name; this.facade = facade; }
        public String open(String client) {
            Logger.log(name, "Єдина операція відкриття"); return facade.open(client);
        }
    }
}
