package ru.itis.javalab.servlets;

import ru.itis.javalab.filters.ResponseUtil;
import ru.itis.javalab.services.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private LoginService loginService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        loginService = (LoginService) req.getServletContext().getAttribute("loginService");

        if (loginService.authenticate(email, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("authenticated", true);
            String redirect = req.getParameter("redirect");

            if (redirect == null) {
                resp.sendRedirect("/profile");
            } else {
                resp.sendRedirect(redirect);
            }
        } else {
            ResponseUtil.sendForbidden(req, resp);
        }
    }
}

