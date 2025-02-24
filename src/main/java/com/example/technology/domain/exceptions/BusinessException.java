package com.example.technology.domain.exceptions;

import com.example.technology.domain.enums.ErrorMessages;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorMessages errorMessage;

    public BusinessException(Throwable cause, ErrorMessages message) {
        super(cause);
        errorMessage = message;
    }

    public BusinessException(ErrorMessages errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
