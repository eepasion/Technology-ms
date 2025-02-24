package com.example.technology.infrastructure.adapters.persistenceadapter;

import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.TechnologyPersistencePort;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.TechnologyEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyPersistenceAdapter implements TechnologyPersistencePort {
    private final TechnologyEntityMapper technologyEntityMapper;
    private final TechnologyRepository technologyRepository;

    @Override
    public Mono<Technology> save(Technology technology) {
        return technologyRepository.save(technologyEntityMapper.toEntity(technology)).map(technologyEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existByName(String name) {
        return technologyRepository.findByName(name).map(technology -> true).defaultIfEmpty(false);
    }

    @Override
    public Flux<Technology> findAllBy(int page, int size, String sort) {
        int offset = (page - 1) * size;
        if ("DESC".equalsIgnoreCase(sort)) {
            return technologyRepository.findAllPagedDesc(size, offset).map(technologyEntityMapper::toModel);
        } else if ("ASC".equalsIgnoreCase(sort)) {
            return technologyRepository.findAllPagedAsc(size, offset).map(technologyEntityMapper::toModel);
        }
        return technologyRepository.findAllPaged(size, offset).map(technologyEntityMapper::toModel);
    }

}
