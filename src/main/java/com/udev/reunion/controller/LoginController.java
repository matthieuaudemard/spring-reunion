package com.udev.reunion.controller;

import com.udev.reunion.domain.User;
import com.udev.reunion.form.SigninForm;
import com.udev.reunion.form.SignupForm;
import com.udev.reunion.service.UserService;
import com.udev.reunion.util.Convertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String loginSubmit(HttpServletRequest request, @ModelAttribute SigninForm form) {
        if (!StringUtils.isEmpty(form.getLogin()) && !StringUtils.isEmpty(form.getPassword())) {
            User user = userService.findByLogin(form.getLogin());
            if (user != null && form.getPassword().equals(user.getPassword())) {
                request.getSession().setAttribute("user", Convertor.convertToDto(user));
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
        model.addAttribute("signinForm", new SigninForm());
        model.addAttribute("signupForm", new SignupForm());
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/login";
    }


    @PostMapping(value = "/register")
    public String registerSubmit(HttpServletRequest request, @ModelAttribute SignupForm form) {
        String login = form.getLogin();
        String fname = form.getFirstname();
        String lname = form.getLastname();
        String password = form.getPassword();
        if (!StringUtils.isEmpty(login) && login.length() < 50 &&
                !StringUtils.isEmpty(fname) && fname.length() < 50 &&
                !StringUtils.isEmpty(lname) && lname.length() < 50 &&
                !StringUtils.isEmpty(password) && password.length() < 50) {
            User user = new User();
            user.setLogin(login);
            user.setFirstname(fname);
            user.setLastname(lname);
            user.setPassword(password);

            try {
                request.getSession().setAttribute("user", Convertor.convertToDto(userService.send(user)));
                return "redirect:/";
            } catch (Exception e) {
                // Si l'inscription échoue, ne rien faire. On passe alors par la redirection suivante.
            }
        }
        return "redirect:/login";
    }


}
