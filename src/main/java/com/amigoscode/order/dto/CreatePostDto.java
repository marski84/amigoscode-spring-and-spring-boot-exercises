package com.amigoscode.order.dto;

public record CreatePostDto(
        String title,
        String body,
        Long userId
) {
}
