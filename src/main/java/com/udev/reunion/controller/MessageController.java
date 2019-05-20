package com.udev.reunion.controller;

import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.model.CommentJson;
import com.udev.reunion.model.MessageJson;
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


        if(request.getSession().getAttribute("userId") != null) {
            model.addAttribute("messages", map(messageService.getLastMessages()));
            return "home";
        } else {
            return "login";
        }

    }

    @GetMapping(value="/message/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMessageById(ModelMap model, @PathVariable Long id, HttpServletRequest request){

        String userId = (String) request.getSession().getAttribute("userId");
        if(userId != null) {
            Message messageById = messageService.getMessageById(id);
            CommentJson commentForm = new CommentJson();
            commentForm.setSenderId(Long.valueOf(userId));
            commentForm.setMessageId(id);
            model.addAttribute("commentForm", commentForm);
            if (messageById != null) {
                MessageJson messageJson = Mapper.convert(messageById);
                messageJson.setCommentJsonList(commentService.getCommentByMessageId(id).stream().map(Mapper::convert).collect(toList()));
                model.addAttribute("singleMessage", messageJson);
                return "message";
            } else {
                return "messageError";
            }
        } else {
            return "login";
        }
    }

    private List<MessageJson> map(List<Message> messages) {
        return messages.stream()
                .map(Mapper::convert)
                .collect(toList());
    }

    @RequestMapping(value = "/create_message", method = RequestMethod.POST)
    @ResponseBody
    public RedirectView createMessage(HttpServletRequest request,
                                      @RequestParam(value = "title", required = true) String title,
                                      @RequestParam(value = "body", required = true) String body) {
        if(request.getSession().getAttribute("userId") != null){
            if (title.length() > 0 && title.length() < 100 && body.length() > 0 && body.length() < 1000) {
                User user = this.userService.findById( Long.parseLong(String.valueOf(request.getSession().getAttribute("userId"))) );

                if (user != null) {

                    Message message = new Message();
                    message.setSender(user);
                    message.setTitle(title);
                    message.setBody(body);

                    if (this.messageService.send(message) != null) {
                        return new RedirectView("/");
                    } else {
                        return new RedirectView("/messageCreateError");
                    }
                } else {
                    return new RedirectView("/messageCreateError");
                }

            } else {
                return new RedirectView("/messageCreateError");
            }
        } else {
            return new RedirectView("/");
        }
    }





}
