package com.meetime.hubspot.config;

import com.meetime.hubspot.utils.RequestFilter;
import com.meetime.hubspot.utils.TokenUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientFilter implements ExchangeFilterFunction {

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {

        if (RequestFilter.ignore(request)) {
            return next.exchange(request);
        }

        String token = TokenUtils.extractTokenFromRequest();
        ClientRequest filteredRequest = ClientRequest.from(request)
                .headers(header -> header.setBearerAuth(token))
                .build();
        return next.exchange(filteredRequest);
    }
}