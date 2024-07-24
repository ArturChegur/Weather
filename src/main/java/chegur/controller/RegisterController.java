package chegur.controller;

import chegur.validator.CredentialsValidator;
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

        String errorMessage = CredentialsValidator.isDataValid(username, password, confirmedPassword);
        WebContext context = createWebContext(req, resp);

        if (errorMessage != null) {
            context.setVariable("errorMessage", errorMessage);
            processTemplate("register", context, resp);
            return;
        }

        processTemplate("home", context, resp);
        //todo add cookies;
    }
}
