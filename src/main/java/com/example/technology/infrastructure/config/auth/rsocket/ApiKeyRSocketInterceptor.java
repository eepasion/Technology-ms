package com.example.technology.infrastructure.config.auth.rsocket;

import com.example.technology.domain.exceptions.BusinessException;
import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.example.technology.domain.enums.ErrorMessages.INTERNAL_ERROR;

@Component
public class ApiKeyRSocketInterceptor implements SocketAcceptor {

    @Value("${security.api-key}")
    private String validApiKey;

    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setupPayload, RSocket rSocket) {
        String metadata = setupPayload.getMetadataUtf8();
        String apiKey = metadata.contains("$") ? metadata.split("\\$", 2)[1] : metadata;
        if (!validApiKey.equals(apiKey.trim())) {
            return Mono.error(new BusinessException(INTERNAL_ERROR));
        }

        return Mono.just(rSocket);
    }
}
