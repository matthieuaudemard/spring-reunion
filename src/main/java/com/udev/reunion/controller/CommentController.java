package com.udev.reunion.controller;


import com.udev.reunion.domain.Comment;
import com.udev.reunion.model.CommentJson;
import com.udev.reunion.service.CommentService;
import com.udev.reunion.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/comments/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLastMessages(ModelMap model, @PathVariable Long messageId) {
        model.addAttribute("comments", map(commentService.getCommentByMessageId(messageId)));
        return "message";
    }

    private List<CommentJson> map(List<Comment> messages) {
        return messages.stream()
                .map(Mapper::convert)
                .collect(toList());
    }
}
