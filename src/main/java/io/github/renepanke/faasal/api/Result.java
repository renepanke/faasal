package io.github.renepanke.faasal.api;

import java.util.Map;

public record Result(Map<String, Object> output, Exception error) {

    public boolean hasError() {
        return error != null;
    }
}
