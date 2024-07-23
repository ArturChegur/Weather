package chegur.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = createWebContext(req, resp);
        processTemplate("register", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmedPassword = req.getParameter("password_repeat");

        String errorMessage = isLoginDataValid(username, password, confirmedPassword);
        WebContext context = createWebContext(req, resp);

        if (errorMessage != null) {
            context.setVariable("errorMessage", errorMessage);
            processTemplate("register", context, resp);
            return;
        }
        processTemplate("home", context, resp);
    }

    private static String isLoginDataValid(String username, String password, String confirmedPassword) {
        if (username == null || username.isBlank()) {
            return "Username can`t be empty";
        }
        if (password == null || password.isBlank() || confirmedPassword == null || confirmedPassword.isBlank()) {
            return "Password can`t be empty";
        }
        if (!password.equals(confirmedPassword)) {
            return "Passwords are not equal";
        }
        if (!username.matches("[a-zA-Z0-9]+")) {
            return "Username should contain only English letters and numbers";
        }
        return null;
    }
}
