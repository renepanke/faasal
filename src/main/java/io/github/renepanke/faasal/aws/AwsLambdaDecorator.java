package io.github.renepanke.faasal.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.github.renepanke.faasal.api.Function;
import io.github.renepanke.faasal.api.Result;

import java.util.HashMap;
import java.util.Map;

public class AwsLambdaDecorator implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final Function function;

    public AwsLambdaDecorator(Function function) {
        this.function = function;
    }

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        this.function.injectLogger(new AwsLambdaLogger(context));

        Result result = function.apply(input);
        Map<String, Object> output = new HashMap<>();

        if (result.hasError()) {
            output.put("statusCode", 500);
            output.put("error", result.error().getMessage());
            return output;
        }
        output.put("statusCode", 200);
        output.put("body", result.output());
        return output;
    }
}
