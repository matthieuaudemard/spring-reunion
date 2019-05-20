package com.udev.reunion.controller;

import com.udev.reunion.domain.User;
import com.udev.reunion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RedirectView login(HttpServletRequest request,
                            @RequestParam(value = "login", required = true) String login,
                            @RequestParam(value = "password", required = true) String password){



        if(!login.isEmpty() && !password.isEmpty()){
            User user = new User();
            user = userService.findByLogin(login);

            if (user != null){
                if(user.getPassword().equals(password)){
                    request.getSession().setAttribute("userId", user.getId().toString());
                    request.getSession().setAttribute("userFirstName", user.getFirstname().toString());
                    request.getSession().setAttribute("userLastName", user.getLastname().toString());
                }
            }
        }

        return new RedirectView("/");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public  RedirectView logout(HttpServletRequest request){
        if(request.getSession().getAttribute("userId") != null){
            request.getSession().removeAttribute("userId");
            request.getSession().removeAttribute("userFirstName");
            request.getSession().removeAttribute("userLastName");
        }

        return new RedirectView("/");
    }






}
