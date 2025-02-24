package com.example.technology.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SuccessMessages {
    TECHNOLOGY_CREATED("Tecnologia creada con exito.");
    private final String message;
}
