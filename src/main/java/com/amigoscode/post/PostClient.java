package com.amigoscode.post;

import com.amigoscode.order.dto.CreatePostDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// TODO: 14 - Create a REST client to call the jsonplaceholder API:
//  - Create a RestClient bean or use RestClient.create("https://jsonplaceholder.typicode.com")
//  - Add a method getPosts() that calls GET /posts and returns List<Post>
//  - Add a method getPostById(Long id) that calls GET /posts/{id} and returns Post
//  Hint:
//    RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
//    Post post = restClient.get()
//        .uri("/posts/{id}", id)
//        .retrieve()
//        .body(Post.class);
@Service
public class PostClient {
    private final RestClient restClient;

    PostClient(final RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Post> getAllPosts() {
        return restClient.get().uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Post>>() {
                });
    }

    public ResponseEntity<Post> createPost(CreatePostDto createPostDto) {
        return restClient.post().uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(createPostDto)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                        })
                .toEntity(Post.class);
    }

    public ResponseEntity<Void> deletePost(Long id) {
        return restClient.delete().uri("posts/{id}", id)
                .retrieve().toBodilessEntity();
    }
}
