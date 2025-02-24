package com.example.technology.infrastructure.config;


import com.example.technology.domain.enums.ErrorMessages;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        ErrorMessages errorMessage = ErrorMessages.fromException(getError(request));
        errorAttributes.put("status", errorMessage.getCode());
        errorAttributes.put("message", errorMessage.getMessage());
        return errorAttributes;
    }
}
