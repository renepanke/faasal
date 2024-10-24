package io.github.renepanke.faasal.api.utils;

public class LoggerUtils {

    public static String compileMessage(String message, Object... args) {
        for (Object arg : args) {
            message = message.replaceFirst("\\{\\}", String.valueOf(arg));
        }
        return message;
    }
}
