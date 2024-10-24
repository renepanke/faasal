package io.github.renepanke.faasal.azure;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import io.github.renepanke.faasal.api.Function;
import io.github.renepanke.faasal.api.Result;

import java.util.Map;

public class AzureFunctionDecorator {

    private final Function function;


    public AzureFunctionDecorator(Function function) {
        this.function = function;
    }

    @FunctionName("SomeAzureFunction")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION)
            HttpRequestMessage<Map<String, Object>> request,
            ExecutionContext context) {

        this.function.injectLogger(new AzureFunctionsLogger(context));

        Result result = function.apply(request.getBody());

        if (result.hasError()) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(result.error().getMessage())
                    .build();
        }

        return request.createResponseBuilder(HttpStatus.OK)
                .body(result.output())
                .build();
    }
}
