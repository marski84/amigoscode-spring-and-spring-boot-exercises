package com.amigoscode.post;

import com.amigoscode.order.dto.CreatePostDto;
import com.oracle.svm.core.annotate.Delete;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: 14 - Create a PostController:
//  - @RestController with @RequestMapping("/api/v1/posts")
//  - Inject PostClient
//  - GET /api/v1/posts — returns all posts from jsonplaceholder
//  - GET /api/v1/posts/{id} — returns a single post
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostClient postClient;
    private final JsonPlaceholderService jsonPlaceholderService;

    PostController(final PostClient postClient,
                   @Qualifier("web-client") final JsonPlaceholderService jsonPlaceholderService) {
        this.postClient = postClient;
        this.jsonPlaceholderService = jsonPlaceholderService;
    }

    @GetMapping()
    public ResponseEntity<List<Post>> getAll() {
        List<Post> posts = postClient.getAllPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePostDto createPostDto) {
        return postClient.createPost(createPostDto);
    }

    @Delete
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        return postClient.deletePost(id);
    }


}
