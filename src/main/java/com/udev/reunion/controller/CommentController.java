package com.udev.reunion.controller;


import com.udev.reunion.domain.Comment;
import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.model.CommentJson;
import com.udev.reunion.model.UserJson;
import com.udev.reunion.service.CommentService;
import com.udev.reunion.service.MessageService;
import com.udev.reunion.service.UserService;
import com.udev.reunion.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
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
                .map(Convertor::convert)
                .collect(toList());
    }

    @PostMapping(value = "/comment/add")
    public RedirectView commentSubmit(HttpServletRequest request, @ModelAttribute CommentJson commentJson) {

        final UserJson userJsonSession = (UserJson) request.getSession().getAttribute("user");
        User user = userService.findById(userJsonSession.getId());
        Message message = commentJson.getMessageId() != null ?
                messageService.getMessageById(commentJson.getMessageId()) : null;

        if (user != null && message != null) {
            Comment comment = new Comment();
            comment.setMessage(message);
            comment.setSender(user);
            comment.setBody(commentJson.getCommentBody());
            commentService.send(comment);
            RedirectView redirectView = new RedirectView();
            redirectView.setContextRelative(true);
            redirectView.setUrl("/message/" + message.getId());
            return redirectView;
        }
        return new RedirectView("/messageCreateError");
    }
}
