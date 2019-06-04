package com.udev.reunion.controller;

import com.udev.reunion.domain.User;
import com.udev.reunion.model.UserJson;
import com.udev.reunion.service.UserService;
import com.udev.reunion.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String loginSubmit(HttpServletRequest request, @RequestParam(value = "password") String password, @ModelAttribute UserJson userJson) {
        if (!userJson.getLogin().isEmpty() && !password.isEmpty()) {
            User user = userService.findByLogin(userJson.getLogin());
            if (user != null && user.getPassword().equals(password)) {
                request.getSession().setAttribute("user", Convertor.convert(user));
                return "redirect:/";
            }
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String login(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new UserJson());
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/login";
    }


    @PostMapping(value = "/register")
    public String registerSubmit(HttpServletRequest request, @ModelAttribute UserJson userJson,
                                 @RequestParam(value = "password") String password) {
        String login = userJson.getLogin();
        String fname = userJson.getFirstname();
        String lname = userJson.getLastname();
        if (!login.isEmpty() && login.length() < 50 &&
                !fname.isEmpty() && fname.length() < 50 &&
                !lname.isEmpty() && lname.length() < 50 &&
                !password.isEmpty() && password.length() < 50) {
            User user = new User();
            user.setLogin(login);
            user.setFirstname(fname);
            user.setLastname(lname);
            user.setPassword(password);

            try {
                request.getSession().setAttribute("user", Convertor.convert(userService.send(user)));
                return "redirect:/";
            } catch (Exception e) {
                // ne rien faire
            }
        }
        return "redirect:/login";
    }


}
