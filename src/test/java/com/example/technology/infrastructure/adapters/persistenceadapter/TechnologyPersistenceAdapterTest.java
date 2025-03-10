package com.example.technology.infrastructure.adapters.persistenceadapter;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.adapters.persistenceadapter.entity.TechnologyEntity;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.TechnologyEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.TechnologyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnologyPersistenceAdapterTest {

    @Mock
    private TechnologyRepository technologyRepository;
    @Mock
    private TechnologyEntityMapper technologyEntityMapper;

    @InjectMocks
    private TechnologyPersistenceAdapter technologyPersistenceAdapter;

    @Test
    void testSaveSuccessful() {

        Technology technology = new Technology(1L, "Java", "Java es un lenguaje de programación");
        TechnologyEntity technologyEntity = new TechnologyEntity(1L, "Java", "Java es un lenguaje de programación");
        when(technologyRepository.save(any(TechnologyEntity.class))).thenReturn(Mono.just(technologyEntity));
        when(technologyEntityMapper.toEntity(any(Technology.class))).thenReturn(technologyEntity);
        when(technologyEntityMapper.toModel(any(TechnologyEntity.class))).thenReturn(technology);

        StepVerifier.create(technologyPersistenceAdapter.save(technology))
                .expectNext(technology)
                .verifyComplete();
        verify(technologyRepository, times(1)).save(any(TechnologyEntity.class));
        verify(technologyEntityMapper, times(1)).toEntity(any(Technology.class));
        verify(technologyEntityMapper, times(1)).toModel(any(TechnologyEntity.class));
    }

    @Test
    void existByNameSuccessful() {

        Technology technology = new Technology(1L, "Java", "Java es un lenguaje de programación");
        TechnologyEntity technologyEntity = new TechnologyEntity(1L, "Java", "Java es un lenguaje de programación");
        when(technologyRepository.findByName(any(String.class))).thenReturn(Mono.just(technologyEntity));

        StepVerifier.create(technologyPersistenceAdapter.existByName(technology.name()))
                .expectNext(true)
                .verifyComplete();
        verify(technologyRepository, times(1)).findByName(any(String.class));
    }

    @Test
    void existByNameReturnEmpty() {
        when(technologyRepository.findByName(any(String.class))).thenReturn(Mono.empty());

        StepVerifier.create(technologyPersistenceAdapter.existByName("Java"))
                .expectNext(false)
                .verifyComplete();
        verify(technologyRepository, times(1)).findByName(any(String.class));
    }

    @Test
    void listTechnologiesBeSuccessful() {
        when(technologyRepository.findAllPaged(10, 0)).thenReturn(Flux.empty());

        StepVerifier.create(technologyPersistenceAdapter.findAllBy(1, 10, "name"))
                .expectNextCount(0)
                .verifyComplete();
        verify(technologyRepository, times(1)).findAllPaged(10, 0);
    }

    @Test
    void listTechnologiesAscBeSuccessful() {
        when(technologyRepository.findAllPagedAsc(anyInt(), anyInt())).thenReturn(Flux.empty());

        StepVerifier.create(technologyPersistenceAdapter.findAllBy(1, 10, "asc"))
                .expectNextCount(0)
                .verifyComplete();
        verify(technologyRepository, times(1)).findAllPagedAsc(10, 0);
        verify(technologyRepository, never()).findAllPagedDesc(10, 0);
        verify(technologyRepository, never()).findAllPaged(10, 0);
    }

    @Test
    void listTechnologiesDescBeSuccessful() {
        when(technologyRepository.findAllPagedDesc(anyInt(), anyInt())).thenReturn(Flux.empty());

        StepVerifier.create(technologyPersistenceAdapter.findAllBy(1, 10, "desc"))
                .expectNextCount(0)
                .verifyComplete();
        verify(technologyRepository, never()).findAllPagedAsc(10, 0);
        verify(technologyRepository, times(1)).findAllPagedDesc(10, 0);
        verify(technologyRepository, never()).findAllPaged(10, 0);
    }

    @Test
    void listTechnologiesOrderNullBeSuccessful() {
        when(technologyRepository.findAllPaged(anyInt(), anyInt())).thenReturn(Flux.empty());

        StepVerifier.create(technologyPersistenceAdapter.findAllBy(1, 10, null))
                .expectNextCount(0)
                .verifyComplete();
        verify(technologyRepository, times(1)).findAllPaged(10, 0);
        verify(technologyRepository, never()).findAllPagedAsc(10, 0);
        verify(technologyRepository, never()).findAllPagedDesc(10, 0);
    }
}
