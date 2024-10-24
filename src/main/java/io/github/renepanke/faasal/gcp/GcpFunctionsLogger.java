package io.github.renepanke.faasal.gcp;

import io.github.renepanke.faasal.api.Logger;
import io.github.renepanke.faasal.api.utils.LoggerUtils;

public class GcpFunctionsLogger implements Logger {

    private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GcpFunctionsLogger.class.getName());

    @Override
    public void debug(String message, Object... args) {
        logger.fine(LoggerUtils.compileMessage(message, args));
    }

    @Override
    public void info(String message, Object... args) {
        logger.info(LoggerUtils.compileMessage(message, args));
    }

    @Override
    public void warn(String message, Object... args) {
        logger.warning(LoggerUtils.compileMessage(message, args));
    }

    @Override
    public void error(String message, Object... args) {
        logger.severe(LoggerUtils.compileMessage(message, args));
    }
}
