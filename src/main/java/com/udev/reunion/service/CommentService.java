package com.udev.reunion.service;

import com.udev.reunion.domain.Comment;
import com.udev.reunion.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) { this.commentRepository = commentRepository; }

    public List<Comment> getCommentByMessageId(Long id) {
        return commentRepository.findCommentsByMessageIdOrderByCommentDate(id);
    }

    public Comment send(Comment comment) {
        return commentRepository.save(comment);
    }

}
