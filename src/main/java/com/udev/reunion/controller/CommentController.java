package com.udev.reunion.controller;


import com.udev.reunion.domain.Comment;
import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.model.CommentJson;
import com.udev.reunion.service.CommentService;
import com.udev.reunion.service.MessageService;
import com.udev.reunion.service.UserService;
import com.udev.reunion.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping
public class CommentController {

    private CommentService commentService;
    private UserService userService;
    private MessageService messageService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, MessageService messageService) {
        this.commentService = commentService;
        this.userService = userService;
        this.messageService = messageService;
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

    @PostMapping(value = "/comment/add")
    public RedirectView commentSubmit(@ModelAttribute CommentJson commentJson) {
        User user = userService.findById(commentJson.getSenderId());
        Message message = messageService.getMessageById(commentJson.getMessageId());

        if (user != null && message != null) {
            Comment comment = new Comment();
            comment.setMessage(message);
            comment.setSender(user);
            comment.setBody(commentJson.getCommentBody());
            commentService.send(comment);
            RedirectView rv = new RedirectView();
            rv.setContextRelative(true);
            rv.setUrl("/message/" + message.getId());
            return rv;
        }

        else {
            return new RedirectView("/messageCreateError");
        }

    }
}
