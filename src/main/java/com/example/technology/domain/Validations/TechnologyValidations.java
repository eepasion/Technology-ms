package com.example.technology.domain.Validations;

import com.example.technology.domain.enums.ErrorMessages;
import com.example.technology.domain.exceptions.BusinessException;
import com.example.technology.domain.model.Technology;

public class TechnologyValidations {

    public static void validateTechnology(Technology technology) {
        validateName(technology.name());
        validateDescription(technology.description());
    }

    public static void validateTechnologyParameters(String sort){
        if(!"ASC".equalsIgnoreCase(sort) && !"DESC".equalsIgnoreCase(sort) && sort != null){
            throw new BusinessException(ErrorMessages.TECHNOLOGY_SORT_FORMAT);
        }};

    private static void validateName(String name) {
        if (name.isEmpty()) {
            throw new BusinessException(ErrorMessages.TECHNOLOGY_NEEDS_NAME);
        }
        if (name.length() > 50) {
            throw new BusinessException(ErrorMessages.TECHNOLOGY_NAME_SIZE);
        }
    }

    private static void validateDescription(String description) {
        if (description.isEmpty()) {
            throw new BusinessException(ErrorMessages.TECHNOLOGY_NEEDS_DESCRIPTION);
        }
        if (description.length() > 90) {
            throw new BusinessException(ErrorMessages.TECHNOLOGY_DESCRIPTION_SIZE);
        }
    }

}
