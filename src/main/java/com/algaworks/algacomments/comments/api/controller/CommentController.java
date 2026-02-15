package com.algaworks.algacomments.comments.api.controller;

import com.algaworks.algacomments.comments.api.client.ModerationClient;
import com.algaworks.algacomments.comments.api.model.CommentInput;
import com.algaworks.algacomments.comments.api.model.CommentOutput;
import com.algaworks.algacomments.comments.api.model.ModerationInput;
import com.algaworks.algacomments.comments.api.model.ModerationOutput;
import com.algaworks.algacomments.comments.domain.model.Comment;
import com.algaworks.algacomments.comments.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final ModerationClient moderationClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput createComment(@RequestBody CommentInput input) {
        ModerationInput moderationInput = ModerationInput.builder()
                .text(input.getText())
                .build();

        ModerationOutput moderationOutput =
                moderationClient.moderateComment(moderationInput);

        if (moderationOutput.isApproved()) {
            Comment comment = Comment.builder()
                    .author(input.getAuthor())
                    .text(input.getText())
                    .build();
            comment = commentRepository.save(comment);
            return convertToModel(comment);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Comment not approved: " + moderationOutput.getReason());
        }

    }

    @GetMapping("/{id}")
    public CommentOutput getComment(@PathVariable String id) {
        return commentRepository.findById(id)
                .map(this::convertToModel)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Page<CommentOutput> listComments(@PageableDefault Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.findAll(pageable);
        return commentsPage.map(this::convertToModel);
    }

    private CommentOutput convertToModel(Comment comment) {
        return CommentOutput.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
