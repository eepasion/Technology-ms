package com.example.technology.domain.validations;

import com.example.technology.domain.enums.ErrorMessages;
import com.example.technology.domain.exceptions.BusinessException;
import com.example.technology.domain.model.Technology;

public class TechnologyValidations {

    private TechnologyValidations() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateTechnology(Technology technology) {
        validateName(technology.name());
        validateDescription(technology.description());
    }

    public static void validateTechnologyParameters(int page, int size, String sort) {
        if (!"ASC".equalsIgnoreCase(sort) && !"DESC".equalsIgnoreCase(sort) && sort != null) {
            throw new BusinessException(ErrorMessages.TECHNOLOGY_SORT_FORMAT);
        }
        if (size <= 0) {
            throw new BusinessException(ErrorMessages.TECHNOLOGY_PARAM_SIZE_LESS_ZERO);
        }
        if (page <= 0) {
            throw new BusinessException(ErrorMessages.TECHNOLOGY_PARAM_PAGE_LESS_ZERO);
        }
    }

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
