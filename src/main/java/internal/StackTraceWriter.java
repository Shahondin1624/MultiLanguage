package internal;

import java.util.StringJoiner;

public class StackTraceWriter {
    public static String transformStackTrace(Throwable t) {
        StringJoiner joiner = new StringJoiner("\n");
        for (StackTraceElement stackTraceElement : t.getStackTrace()) {
            joiner.add(stackTraceElement.toString());
        }
        return joiner.toString();
    }
}
