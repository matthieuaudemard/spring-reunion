package com.udev.reunion.controller;

import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.model.MessageJson;
import com.udev.reunion.model.UserJson;
import com.udev.reunion.service.MessageService;
import com.udev.reunion.service.UserService;
import com.udev.reunion.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public UserController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserById(HttpServletRequest request, ModelMap model, @PathVariable Long userId) {
        UserJson userSessionJson = (UserJson) request.getSession().getAttribute("user");
        if (userSessionJson != null) {
            User sessionUser = userService.findById(userSessionJson.getId());
            User userById = userService.findById(userId);
            if (sessionUser != null) {
                UserJson userJson = Convertor.convert(userById);
                model.addAttribute("user", userJson);
                model.addAttribute("messages", map(messageService.getUserMessages(userId)));
                return "user";
            }
        }
        return "login";
    }

    @GetMapping(value = "/user/search/{pattern}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<UserJson> userSearch(@PathVariable String pattern, HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            return userService.findByPattern(pattern)
                    .stream()
                    .map(Convertor::convert)
                    .collect(toList());
        }
        return Collections.emptyList();
    }

    private List<MessageJson> map(List<Message> messages) {
        return messages.stream()
                .map(Convertor::convert)
                .collect(toList());
    }
}
