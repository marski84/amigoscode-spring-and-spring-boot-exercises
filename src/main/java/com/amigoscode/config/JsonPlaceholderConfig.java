package com.amigoscode.config;

import com.amigoscode.post.JsonPlaceholderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class JsonPlaceholderConfig {

    @Bean("rest-client")
    JsonPlaceholderService jsonPlaceholderRestClientService() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(JsonPlaceholderService.class);
    }

    @Bean("web-client")
    JsonPlaceholderService jsonPlaceholderWebClientService() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .defaultStatusHandler(HttpStatusCode::isError, clientResponse ->
                {
                    throw new ResponseStatusException(clientResponse.statusCode());
                })
                .build();

        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build()
                .createClient(JsonPlaceholderService.class);
    }
}
