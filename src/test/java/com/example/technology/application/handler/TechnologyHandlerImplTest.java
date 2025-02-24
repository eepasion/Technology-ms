package com.example.technology.application.handler;

import com.example.technology.application.dto.TechnologyDTO;
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
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

}