package com.udev.reunion.controller;

import com.udev.reunion.domain.Message;
import com.udev.reunion.model.MessageJson;
import com.udev.reunion.service.MessageService;
import com.udev.reunion.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = {"/", "/last"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLastMessages(ModelMap model) {
        model.addAttribute("messages", map(messageService.getLastMessages()));
        return "home";
    }

    private List<MessageJson> map(List<Message> messages) {
        return messages.stream()
                .map(Mapper::convert)
                .collect(toList());
    }
}
