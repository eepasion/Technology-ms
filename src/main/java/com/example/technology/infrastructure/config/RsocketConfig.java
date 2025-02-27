package com.example.technology.infrastructure.config;

import com.example.technology.infrastructure.config.auth.rsocket.ApiKeyRSocketInterceptor;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;

@Configuration
public class RsocketConfig {
    @Bean
    public CloseableChannel rSocketServer(RSocketMessageHandler messageHandler, ApiKeyRSocketInterceptor apiKeyRSocketInterceptor) {
        SocketAcceptor responder = messageHandler.responder();

        return RSocketServer.create((setupPayload, sendingSocket) ->
                apiKeyRSocketInterceptor.accept(setupPayload, sendingSocket)
                        .flatMap(authenticatedSocket -> responder.accept(setupPayload, authenticatedSocket))
        ).bindNow(TcpServerTransport.create("localhost", 7000));
    }
}
