package com.algaworks.algacomments.comments.domain.repository;

import com.algaworks.algacomments.comments.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
