package com.example.technology.domain.usecase;

import com.example.technology.domain.api.TechnologyServicePort;
import com.example.technology.domain.enums.ErrorMessages;
import com.example.technology.domain.exceptions.BusinessException;
import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.TechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.technology.domain.Validations.TechnologyValidations.validateTechnology;
import static com.example.technology.domain.Validations.TechnologyValidations.validateTechnologyParameters;

@RequiredArgsConstructor
public class TechnologyUseCase implements TechnologyServicePort {
    private final TechnologyPersistencePort technologyPersistencePort;
    @Override
    public Mono<Technology> save(Technology technology) {
        validateTechnology(technology);
        return technologyPersistencePort.existByName(technology.name()).
                filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessages.TECHNOLOGY_ALREADY_EXISTS)))
                .flatMap(exists -> technologyPersistencePort.save(technology));
    }

    @Override
    public Flux<Technology> findAllBy(int page, int size, String sort) {
        validateTechnologyParameters(sort);
        return technologyPersistencePort.findAllBy(page,size,sort);
    }
}
