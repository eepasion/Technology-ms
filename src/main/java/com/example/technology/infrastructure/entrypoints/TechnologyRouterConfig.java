package com.example.technology.infrastructure.entrypoints;

import com.example.technology.application.dto.TechnologyDTO;
import com.example.technology.application.handler.TechnologyHandlerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
public class TechnologyRouterConfig {
    @RouterOperations({
            @RouterOperation(
                    path = "/technology", beanClass = TechnologyHandlerImpl.class, beanMethod = "saveTechnology",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "opSaveTechnology",
                            requestBody = @RequestBody(
                                    description = "Datos de la tecnología a crear",
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = TechnologyDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Tecnología creada exitosamente"),
                                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/technology", beanClass = TechnologyHandlerImpl.class, beanMethod = "getAllTechnologies",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "opGetAllTechnologies",
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "size", required = false),
                                    @Parameter(in = ParameterIn.QUERY, name = "page", required = false),
                                    @Parameter(in = ParameterIn.QUERY, name = "sort", required = false)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Tecnologías obtenidas exitosamente"),
                                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> routerFunction(TechnologyHandlerImpl technologyHandler) {
        return route(POST("/technology"), technologyHandler::saveTechnology)
                .andRoute(GET("/technology"), technologyHandler::getAllTechnologies);
    }
}
