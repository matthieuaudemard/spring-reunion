package com.udev.reunion.repository;

import com.udev.reunion.domain.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findCommentsByMessageIdOrderByCommentDate(Long i);
}
