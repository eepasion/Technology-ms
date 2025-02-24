package com.example.technology.infrastructure.adapters.persistenceadapter.repository;

import com.example.technology.infrastructure.adapters.persistenceadapter.entity.TechnologyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TechnologyRepository extends ReactiveCrudRepository<TechnologyEntity,Long> {
    Mono<TechnologyEntity> findByName(String name);

    @Query("SELECT * FROM technologies LIMIT :limit OFFSET :offset")
    Flux<TechnologyEntity> findAllPaged(int limit, int offset);

    @Query("SELECT * FROM technologies ORDER BY nombre ASC LIMIT :limit OFFSET :offset")
    Flux<TechnologyEntity> findAllPagedAsc(int limit, int offset);

    @Query("SELECT * FROM technologies ORDER BY nombre DESC LIMIT :limit OFFSET :offset")
    Flux<TechnologyEntity> findAllPagedDesc(int limit, int offset);
}
