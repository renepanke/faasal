package io.github.renepanke.faasal.azure;

import com.microsoft.azure.functions.ExecutionContext;
import io.github.renepanke.faasal.api.Logger;
import io.github.renepanke.faasal.api.utils.LoggerUtils;

public class AzureFunctionsLogger implements Logger {

    private final ExecutionContext context;

    public AzureFunctionsLogger(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public void debug(String message, Object... args) {
        context.getLogger().fine(LoggerUtils.compileMessage(message, args));
    }

    @Override
    public void info(String message, Object... args) {
        context.getLogger().info(LoggerUtils.compileMessage(message, args));
    }

    @Override
    public void warn(String message, Object... args) {
        context.getLogger().warning(LoggerUtils.compileMessage(message, args));
    }

    @Override
    public void error(String message, Object... args) {
        context.getLogger().severe(LoggerUtils.compileMessage(message, args));
    }
}
