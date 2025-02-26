package com.example.technology.application.handler;

import com.example.technology.application.dto.TechnologyDTO;
import com.example.technology.application.dto.TechnologyResponseDTO;
import com.example.technology.application.mapper.TechnologyMapper;
import com.example.technology.domain.api.TechnologyServicePort;
import com.example.technology.domain.model.Technology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@ExtendWith(MockitoExtension.class)
class TechnologyHandlerImplTest {
    @Mock
    private TechnologyServicePort technologyServicePort;
    @Mock
    private TechnologyMapper technologyMapper;
    @InjectMocks
    private TechnologyHandlerImpl technologyHandler;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(
                route(POST("/technology"), technologyHandler::saveTechnology)
                        .andRoute(GET("/technology"), technologyHandler::getAllTechnologies)
        ).build();
    }

    @Test
    void testSaveTechnologySuccessful() {
        TechnologyDTO technologyDTO = new TechnologyDTO("Java", "Programming Language");
        Technology technology = new Technology(1L, "Java", "Programming Language");

        when(technologyMapper.toModel(any(TechnologyDTO.class))).thenReturn(technology);
        when(technologyServicePort.save(technology)).thenReturn(Mono.just(technology));
        when(technologyMapper.toDTO(technology)).thenReturn(technologyDTO);

        webTestClient.post().uri("/technology").bodyValue(technologyDTO).exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Tecnologia creada con exito.");
    }

    @Test
    void testListTechnologiesSuccessful() {
        TechnologyDTO technologyDTO = new TechnologyDTO("Java", "Programming Language");
        Technology technology = new Technology(1L, "Java", "Programming Language");
        when(technologyServicePort.findAllBy(anyInt(), anyInt(), anyString())).thenReturn(Flux.just(technology));
        when(technologyMapper.toDTO(any(Technology.class))).thenReturn(technologyDTO);
        webTestClient.get().uri(uriBuilder -> uriBuilder.path("/technology")
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .queryParam("sort", "DESC")
                        .build()).exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetTechnologiesByIdSuccessful() {
        Technology technology = new Technology(1L, "Java", "Programming Language");
        TechnologyResponseDTO technologyResponseDTO = new TechnologyResponseDTO(1L, "Java");
        List<Long> ids = List.of(1L);
        when(technologyServicePort.findAllById(anyList())).thenReturn(Flux.just(technology));
        when(technologyMapper.toResponseDTO(any(Technology.class))).thenReturn(technologyResponseDTO);

        StepVerifier.create(technologyHandler.getTechnologiesById(ids)).expectNext(technologyResponseDTO).verifyComplete();

        verify(technologyServicePort, times(1)).findAllById(ids);
        verify(technologyMapper, times(1)).toResponseDTO(technology);
    }

}