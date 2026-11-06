package com.amigoscode.post;

import com.amigoscode.order.dto.CreatePostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

public interface JsonPlaceholderService {

    @GetExchange("/posts")
    List<Post> getAllPosts();

    @PostExchange("/posts")
    ResponseEntity<Post> createPost(CreatePostDto createPostDto);

    @DeleteExchange("/posts/{id}")
    ResponseEntity<Void> deletePost(@PathVariable Long id);

}
