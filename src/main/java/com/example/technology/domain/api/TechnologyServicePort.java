package com.example.technology.domain.api;

import com.example.technology.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TechnologyServicePort {
    Mono<Technology> save(Technology technology);
    Flux<Technology> findAllBy(int page, int size, String sort);
}
