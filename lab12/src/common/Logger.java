package common;
/** Deterministic logical time; every physical output line has the required prefix. */
public final class Logger {
    private static int tick;
    private Logger() {}
    public static void log(String source, Object event) {
        for (String line : String.valueOf(event).split("\\R"))
            System.out.printf("[%04d] [%s] %s%n", ++tick, source, line);
    }
}
