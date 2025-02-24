package com.example.technology.application.handler;

import com.example.technology.domain.api.TechnologyServicePort;
import com.example.technology.domain.enums.SuccessMessages;
import com.example.technology.application.dto.TechnologyDTO;
import com.example.technology.application.mapper.TechnologyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class TechnologyHandlerImpl {
    private final TechnologyMapper technologyMapper;
    private final TechnologyServicePort technologyServicePort;

    public Mono<ServerResponse> saveTechnology(ServerRequest request) {
        return request.bodyToMono(TechnologyDTO.class)
                .map(dto -> {
                    dto.setName(dto.getName().trim());
                    dto.setDescription(dto.getDescription().trim());
                    return dto;
                })
                .flatMap(technologyDto -> technologyServicePort.save(technologyMapper.toModel(technologyDto)).map(technologyMapper::toDTO))
                .flatMap(savedTechnology -> {
                    Map<String, String> response = Map.of("message", SuccessMessages.TECHNOLOGY_CREATED.getMessage());
                    return ServerResponse.status(HttpStatus.CREATED).bodyValue(response);
                });
    }

    public Mono<ServerResponse> getAllTechnologies(ServerRequest request) {
        int page = request.queryParam("page").map(Integer::parseInt).orElse(1);
        int size = request.queryParam("size").map(Integer::parseInt).orElse(10);
        String sort = request.queryParam("sort").orElse(null);
        return technologyServicePort.findAllBy(page, size, sort)
                .doOnError(error -> log.error(error.getMessage()))
                .map(technologyMapper::toDTO)
                .collectList()
                .flatMap(technologies -> ServerResponse.ok().bodyValue(technologies));
    }
}
