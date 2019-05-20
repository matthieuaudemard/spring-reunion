package com.udev.reunion.repository;

import com.udev.reunion.domain.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.message.id = :id ORDER BY commentDate ASC")
    List<Comment> findCommentsByMessageIdOrderByCommentDate(@Param("id") Long id);
}
