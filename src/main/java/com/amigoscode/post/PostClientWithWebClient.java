package com.amigoscode.post;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
class PostClientWithWebClient {
    private final WebClient webClient;
    private final String uri = "https://jsonplaceholder.typicode.com";

    PostClientWithWebClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<List<Post>> getAllPosts() {
        return webClient.get().uri("/posts").retrieve().
                bodyToFlux(new ParameterizedTypeReference<>() {
                });
    }
}
