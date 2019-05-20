package com.udev.reunion.controller;

import com.udev.reunion.domain.User;
import com.udev.reunion.service.UserService;
import com.udev.reunion.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public RedirectView login(HttpServletRequest request,
                              @RequestParam(value = "login") String login,
                              @RequestParam(value = "password") String password) {
        if (!login.isEmpty() && !password.isEmpty()) {
            User user = userService.findByLogin(login);
            if (user != null && user.getPassword().equals(password)) {
                request.getSession().setAttribute("user", Convertor.convert(user));
            }
        }

        return new RedirectView("/");
    }

    @GetMapping(value = "/logout")
    public RedirectView logout(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            request.getSession().removeAttribute("user");
        }
        return new RedirectView("/");
    }


    @PostMapping(value = "/register")
    @ResponseBody
    public RedirectView register(HttpServletRequest request,
                                 @RequestParam(value = "login") String login,
                                 @RequestParam(value = "fname") String fname,
                                 @RequestParam(value = "lname") String lname,
                                 @RequestParam(value = "password") String password
    ) {
        if (!login.isEmpty() && login.length() < 50 &&
                !fname.isEmpty() && fname.length() < 50 &&
                !lname.isEmpty() && lname.length() < 50 &&
                !password.isEmpty() && password.length() < 50) {
            User user = new User();
            user.setLogin(login);
            user.setFirstname(fname);
            user.setLastname(lname);
            user.setPassword(password);

            user = userService.send(user);
            if (user != null) {
                request.getSession().setAttribute("user", Convertor.convert(user));
            }
        }
        return new RedirectView("/");
    }


}
