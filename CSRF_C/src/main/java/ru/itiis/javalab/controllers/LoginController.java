package ru.itiis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itiis.javalab.filters.ResponseUtil;
import ru.itiis.javalab.services.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String signInPage() {
        return "login";
    }

    @PostMapping("/login")
    public String auth(String email, String password, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (loginService.authenticate(email, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("authenticated", true);
            String redirect = req.getParameter("redirect");

            if (redirect == null) {
                return "redirect:/profile";
            } else {
                return "redirect:/" + redirect;
            }
        }
        ResponseUtil.sendForbidden(req, resp);
        return "";
    }
}
