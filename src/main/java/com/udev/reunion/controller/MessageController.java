package com.udev.reunion.controller;

import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.model.CommentJson;
import com.udev.reunion.model.MessageJson;
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
        UserJson user = (UserJson) request.getSession().getAttribute("user");
        if (user != null) {
            MessageJson messageForm = new MessageJson();
            messageForm.setSender(user);
            model.addAttribute("messageForm", messageForm);
            model.addAttribute("messages", map(messageService.getLastMessages()));
            return "home";
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping(value = "/message/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMessageById(ModelMap model, @PathVariable Long id, HttpServletRequest request) {
        UserJson user = (UserJson) request.getSession().getAttribute("user");
        if (user != null) {
            Message messageById = messageService.getMessageById(id);
            CommentJson commentForm = new CommentJson();
            commentForm.setSender(user);
            commentForm.setMessageId(id);
            model.addAttribute("commentForm", commentForm);
            if (messageById != null) {
                MessageJson messageJson = Convertor.convert(messageById);
                messageJson.setCommentJsonList(
                        commentService.getCommentByMessageId(id)
                                .stream()
                                .map(Convertor::convert)
                                .collect(toList())
                );
                model.addAttribute("message", messageJson);
                return "message";
            } else {
                return "messageError";
            }
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteMessage(HttpServletRequest request, @PathVariable Long id) {
        UserJson user = (UserJson) request.getSession().getAttribute("user");
        if (user != null) {
            this.messageService.delete(id);
        }
        return "redirect:/";
    }

    private List<MessageJson> map(List<Message> messages) {
        return messages.stream()
                .map(Convertor::convert)
                .collect(toList());
    }

    @PostMapping(value = "/create_message")
    public String messageSubmit(HttpServletRequest request, @ModelAttribute MessageJson messageJson) {
        UserJson userJsonSession = (UserJson) request.getSession().getAttribute("user");
        if (userJsonSession != null) {
            String messageTitle = messageJson.getMessageTitle();
            String messageBody = messageJson.getMessageBody();
            if (messageTitle.length() > 0 && messageTitle.length() < 100 &&
                    messageBody.length() > 0 && messageBody.length() < 1000) {
                User user = userService.findById(userJsonSession.getId());
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
