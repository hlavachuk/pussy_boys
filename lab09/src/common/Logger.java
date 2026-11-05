package common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Єдиний формат журналу: [час] [джерело] подія. */
public final class Logger {
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private Logger() {
    }

    public static void log(String source, String event) {
        System.out.println("[" + LocalTime.now().format(TIME) + "] [" + source + "] " + event);
    }

    /** Емуляція "дорогої" операції затримкою. */
    public static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
