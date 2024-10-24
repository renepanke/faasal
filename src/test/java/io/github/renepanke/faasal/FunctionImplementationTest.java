package io.github.renepanke.faasal;

import io.github.renepanke.faasal.api.Function;
import io.github.renepanke.faasal.api.Result;

import java.util.HashMap;
import java.util.Map;

public class FunctionImplementationTest extends Function {

    @Override
    public Result apply(Map<String, Object> input) {
        this.logger.info("Hello World");
        return new Result(new HashMap<>(), null);
    }
}
