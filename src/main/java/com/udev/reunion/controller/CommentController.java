package com.udev.reunion.controller;


import com.udev.reunion.domain.Comment;
import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.dto.CommentDto;
import com.udev.reunion.dto.UserDto;
import com.udev.reunion.form.CommentForm;
import com.udev.reunion.service.CommentService;
import com.udev.reunion.service.MessageService;
import com.udev.reunion.service.UserService;
import com.udev.reunion.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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
    @ResponseBody
    public List<CommentDto> getLastMessages(HttpServletRequest request, @PathVariable Long messageId) {
        if (request.getSession().getAttribute("user") == null) {
            return Collections.emptyList();
        }

        // Renvoi de la liste de CommentDto
        return commentService.getCommentByMessageId(messageId)
                .stream()
                .map(Convertor::convertToDto)
                .collect(toList());
    }

    @PostMapping(value = "/comment/add")
    public String commentSubmit(HttpServletRequest request, @ModelAttribute CommentForm form) {

        final UserDto userDtoSession = (UserDto) request.getSession().getAttribute("user");
        final User user = userService.findById(userDtoSession.getId());
        final Message message = form.getMessageId() != null ? messageService.getMessageById(form.getMessageId()) : null;

        if (user != null && message != null) {
            Comment comment = new Comment();
            comment.setMessage(message);
            comment.setSender(user);
            comment.setBody(form.getCommentBody());
            if (commentService.send(comment) != null) {
                return "redirect:/message/" + message.getId();
            }
        }
        return "redirect:/messageCreateError";
    }
}
