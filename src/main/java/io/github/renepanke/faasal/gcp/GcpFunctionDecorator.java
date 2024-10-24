package io.github.renepanke.faasal.gcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import io.github.renepanke.faasal.api.Function;
import io.github.renepanke.faasal.api.Result;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public class GcpFunctionDecorator implements HttpFunction {

    private final Function function;

    public GcpFunctionDecorator(Function function) {
        this.function = function;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        this.function.injectLogger(new GcpFunctionsLogger());

        BufferedWriter writer = httpResponse.getWriter();
        try {
            Map<String, Object> input = parseRequestBody(httpRequest);
            Result result = function.apply(input);

            if (result.hasError()) {
                httpResponse.setStatusCode(500);
                writer.write(result.error().getMessage());
                return;
            }
            httpResponse.setStatusCode(200);
            writer.write(result.output().toString());
        } catch (JsonProcessingException e) {
            httpResponse.setStatusCode(400);
            writer.write(e.getMessage());
        }
    }

    private Map<String, Object> parseRequestBody(HttpRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), new TypeReference<>() {});
    }
}
