package common;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public final class Logger {
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private Logger() {}
    public static void log(String source, String event) {
        for (String line : event.split("\\R", -1)) {
            System.out.println("[" + LocalTime.now().format(TIME) + "] [" + source + "] " + line);
        }
    }
}
