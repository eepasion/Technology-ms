package com.example.technology.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessages {
    INTERNAL_ERROR(500, "Hubo un error, por favor intente nuevamente mas tarde."),
    TECHNOLOGY_NOT_FOUND(404, "La tecnologia no se encuentra registrada."),
    TECHNOLOGY_ALREADY_EXISTS(400, "La tecnologia ya se encuentra registrada."),
    TECHNOLOGY_NEEDS_DESCRIPTION(400, "La tecnologia debe tener una descripcion."),
    TECHNOLOGY_NEEDS_NAME(400, "La tecnologia debe tener un nombre."),
    TECHNOLOGY_NAME_SIZE(400, "El nombre de la tecnologia debe tener un maximo de 50 caracteres."),
    TECHNOLOGY_DESCRIPTION_SIZE(400, "La descripcion de la tecnologia debe tener un maximo de 90 caracteres."),
    TECHNOLOGY_SORT_FORMAT(400, "El parametro sort debe ser 'asc' o 'desc'."),
    TECHNOLOGY_PARAM_SIZE_LESS_ZERO(400, "El parametro size no debe ser menor a 1"),
    TECHNOLOGY_PARAM_PAGE_LESS_ZERO(400, "El parametro page no debe ser menor a 1");

    private final Number code;
    private final String message;

    public static ErrorMessages fromException(Throwable ex) {
        for (ErrorMessages error : values()) {
            if (ex.getMessage().contains(error.message)) {
                return error;
            }
        }
        return INTERNAL_ERROR;
    }

}

