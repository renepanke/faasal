package io.github.renepanke.faasal.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.logging.LogLevel;
import io.github.renepanke.faasal.api.Logger;
import io.github.renepanke.faasal.api.utils.LoggerUtils;

public class AwsLambdaLogger implements Logger {

    private final Context context;

    public AwsLambdaLogger(Context context) {
        this.context = context;
    }

    @Override
    public void debug(String message, Object... args) {
        context.getLogger().log(LoggerUtils.compileMessage(message, args), LogLevel.DEBUG);
    }

    @Override
    public void info(String message, Object... args) {
        context.getLogger().log(LoggerUtils.compileMessage(message, args), LogLevel.INFO);
    }

    @Override
    public void warn(String message, Object... args) {
        context.getLogger().log(LoggerUtils.compileMessage(message, args), LogLevel.WARN);
    }

    @Override
    public void error(String message, Object... args) {
        context.getLogger().log(LoggerUtils.compileMessage(message, args), LogLevel.ERROR);
    }
}
