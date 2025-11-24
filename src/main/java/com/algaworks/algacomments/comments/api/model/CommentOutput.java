package com.algaworks.algacomments.comments.api.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentOutput {
    private Long id;
    private String text;
    private String author;
    private LocalDateTime createdAt;
}
