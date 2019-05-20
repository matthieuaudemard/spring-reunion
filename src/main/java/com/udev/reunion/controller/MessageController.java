package com.udev.reunion.controller;

import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.model.MessageJson;
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

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping(value = "/messageCreateError" )
    public String redirectToError(){
        return "messageCreateError";
    }


    @GetMapping(value = {"/", "/last"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLastMessages(ModelMap model) {
        model.addAttribute("messages", map(messageService.getLastMessages()));
        return "home";
    }

    @GetMapping(value="/message/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMessageById(ModelMap model, @PathVariable Long id){

        Message messageById = messageService.getMessageById(id);
        if(messageById != null) {
            model.addAttribute("singleMessage", Mapper.convert(messageById));
            return "message";
        }else{
            return "messageError";
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
                                @RequestParam(value="user", required=true) String userStr,
                                @RequestParam(value="title", required=true) String title,
                                @RequestParam(value = "body", required=true) String body){

        if( userStr.matches("\\d+") && title.length() > 0 && title.length()<100 && body.length() > 0 && body.length() < 1000 ){
            Long userLong = Long.parseLong(userStr);
            User user = this.userService.findById(userLong);

            if(user != null){

                Message message = new Message();
                message.setSender(user);
                message.setTitle(title);
                message.setBody(body);

                if (this.messageService.send(message) != null) {
                    return new RedirectView("/");
                }

                else {
                    return new RedirectView("/messageCreateError");
                }



            } else {
                return new RedirectView("/messageCreateError");
            }

        }else{
            return new RedirectView("/messageCreateError");
        }



    }



}
