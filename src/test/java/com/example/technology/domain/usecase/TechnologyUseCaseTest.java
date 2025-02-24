package com.example.technology.domain.usecase;

import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.TechnologyPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {

    @Mock
    private TechnologyPersistencePort technologyPersistencePort;

    @InjectMocks
    private TechnologyUseCase technologyUseCase;

    private Technology technology;

    @BeforeEach
    void setUp() {
        technology = new Technology(1L, "Java", "Java es un lenguaje de programación");
    }

    @Test
    void saveTechnologyBeSuccessful() {
        when(technologyPersistencePort.existByName(technology.name())).thenReturn(Mono.just(false));
        when(technologyPersistencePort.save(technology)).thenReturn(Mono.just(technology));

        StepVerifier.create(technologyUseCase.save(technology))
                .expectNext(technology)
                .verifyComplete();

        verify(technologyPersistencePort, times(1)).existByName(technology.name());
        verify(technologyPersistencePort, times(1)).save(technology);
    }
  
}