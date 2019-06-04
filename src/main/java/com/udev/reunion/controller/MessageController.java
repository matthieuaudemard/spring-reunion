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
import org.springframework.web.servlet.view.RedirectView;

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
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("messages", map(messageService.getLastMessages()));
            return "home";
        } else {
            return "login";
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
                messageJson.setCommentJsonList(commentService.getCommentByMessageId(id).stream().map(Convertor::convert).collect(toList()));
                model.addAttribute("message", messageJson);
                return "message";
            } else {
                return "messageError";
            }
        }
        return "login";
    }

    @GetMapping(value = "/delete/{id}")
    public RedirectView deleteMessage(HttpServletRequest request, @PathVariable Long id) {
        UserJson user = (UserJson) request.getSession().getAttribute("user");
        if (user != null) {
            this.messageService.delete(id);
        }
        return new RedirectView("/");
    }

    private List<MessageJson> map(List<Message> messages) {
        return messages.stream()
                .map(Convertor::convert)
                .collect(toList());
    }

    @PostMapping(value = "/create_message")
    @ResponseBody
    public RedirectView createMessage(HttpServletRequest request,
                                      @RequestParam(value = "title") String title,
                                      @RequestParam(value = "body") String body) {
        UserJson userJsonSession = (UserJson) request.getSession().getAttribute("user");
        if (userJsonSession != null) {
            if (title.length() > 0 && title.length() < 100 && body.length() > 0 && body.length() < 1000) {
                User user = userService.findById(userJsonSession.getId());
                if (user != null) {
                    Message message = new Message();
                    message.setSender(user);
                    message.setTitle(title);
                    message.setBody(body);
                    message = messageService.send(message);
                    if (message != null) {
                        RedirectView redirectToSentMessage = new RedirectView();
                        redirectToSentMessage.setContextRelative(true);
                        redirectToSentMessage.setUrl("/message/" + message.getId());
                        return redirectToSentMessage;
                    }
                }
            }
            return new RedirectView("/messageCreateError");
        }
        return new RedirectView("/");
    }


}
