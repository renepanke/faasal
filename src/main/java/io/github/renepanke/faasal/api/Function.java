package io.github.renepanke.faasal.api;

import java.util.Map;

public abstract class Function {

    protected Logger logger;

    public abstract Result apply(Map<String,Object> input);

    public void injectLogger(Logger logger) {
        this.logger = logger;
    }
}
