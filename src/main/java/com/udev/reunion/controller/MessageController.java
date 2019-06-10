package com.udev.reunion.controller;

import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.dto.MessageDto;
import com.udev.reunion.dto.UserDto;
import com.udev.reunion.form.CommentForm;
import com.udev.reunion.form.MessageForm;
import com.udev.reunion.service.CommentService;
import com.udev.reunion.service.MessageService;
import com.udev.reunion.service.UserService;
import com.udev.reunion.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, CommentService commentService) {
        this.messageService = messageService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping(value = "/messageCreateError")
    public String redirectToError() {
        return "messageCreateError";
    }


    @GetMapping(value = {"/", "/last"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLastMessages(ModelMap model, HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        if (user != null) {
            model.addAttribute("messageForm", new MessageForm());
            model.addAttribute("messages", map(messageService.getLastMessages()));
            return "home";
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping(value = "/message/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMessageById(ModelMap model, @PathVariable Long id, HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        if (user != null) {
            Message messageById = messageService.getMessageById(id);
            CommentForm commentForm = new CommentForm();
            commentForm.setMessageId(id);
            model.addAttribute("commentForm", commentForm);
            if (messageById != null) {
                MessageDto messageDto = Convertor.convertToDto(messageById);
                messageDto.setCommentJsonList(
                        commentService.getCommentByMessageId(id)
                                .stream()
                                .map(Convertor::convertToDto)
                                .collect(toList())
                );
                model.addAttribute("message", messageDto);
                return "message";
            } else {
                return "messageError";
            }
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteMessage(HttpServletRequest request, @PathVariable Long id) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        if (user != null) {
            this.messageService.delete(id);
        }
        return "redirect:/";
    }

    private List<MessageDto> map(List<Message> messages) {
        return messages.stream()
                .map(Convertor::convertToDto)
                .collect(toList());
    }

    @PostMapping(value = "/create_message")
    public String messageSubmit(HttpServletRequest request, @ModelAttribute MessageForm form) {
        UserDto userDtoSession = (UserDto) request.getSession().getAttribute("user");
        if (userDtoSession != null) {
            String messageTitle = form.getMessageTitle();
            String messageBody = form.getMessageBody();
            if (messageTitle.length() > 0 && messageTitle.length() < 100 &&
                    messageBody.length() > 0 && messageBody.length() < 1000) {
                User user = userService.findById(userDtoSession.getId());
                if (user != null) {
                    Message message = new Message();
                    message.setSender(user);
                    message.setTitle(messageTitle);
                    message.setBody(messageBody);
                    message = messageService.send(message);
                    return "redirect:/message/" + message.getId();
                }
            }
            return "redirect:/messageCreateError";
        }
        return "redirect:/login";
    }
}
