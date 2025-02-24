package com.example.technology.domain.validations;

import com.example.technology.domain.enums.ErrorMessages;
import com.example.technology.domain.exceptions.BusinessException;
import com.example.technology.domain.model.Technology;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TechnologyValidationsTest {

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Technology technology = new Technology(1L, "", "Java es un lenguaje de programación");
        assertThatThrownBy(() -> TechnologyValidations.validateTechnology(technology))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TECHNOLOGY_NEEDS_NAME.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaxLength() {
        Technology technology = new Technology(1L, "A".repeat(51), "Descripción válida");

        assertThatThrownBy(() -> TechnologyValidations.validateTechnology(technology))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TECHNOLOGY_NAME_SIZE.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        Technology technology = new Technology(1L, "Java", "");

        assertThatThrownBy(() -> TechnologyValidations.validateTechnology(technology))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TECHNOLOGY_NEEDS_DESCRIPTION.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        Technology technology = new Technology(1L, "Java", "A".repeat(91));

        assertThatThrownBy(() -> TechnologyValidations.validateTechnology(technology))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TECHNOLOGY_DESCRIPTION_SIZE.getMessage());
    }

    @Test
    void shouldPassValidationWithValidTechnology() {
        Technology technology = new Technology(1L, "Java", "Lenguaje de programación");

        assertThatCode(() -> TechnologyValidations.validateTechnology(technology))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionWhenSortIsInvalid() {
        assertThatThrownBy(() -> TechnologyValidations.validateTechnologyParameters(1, 1, "invalid-sort"))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TECHNOLOGY_SORT_FORMAT.getMessage());
    }

    @Test
    void shouldPassValidationWithASC() {
        assertThatCode(() -> TechnologyValidations.validateTechnologyParameters(1, 1, "asc"))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldPassValidationWithDESC() {
        assertThatCode(() -> TechnologyValidations.validateTechnologyParameters(1, 1, "desc"))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldPassValidationWithNull() {
        assertThatCode(() -> TechnologyValidations.validateTechnologyParameters(1, 1, null))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionWhenPageIsZero() {
        assertThatThrownBy(() -> TechnologyValidations.validateTechnologyParameters(0, 1, null))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TECHNOLOGY_PARAM_PAGE_LESS_ZERO.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZero() {
        assertThatThrownBy(() -> TechnologyValidations.validateTechnologyParameters(1, 0, null))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorMessages.TECHNOLOGY_PARAM_SIZE_LESS_ZERO.getMessage());
    }
}