package chegur.controller.impl;

import chegur.controller.BaseController;
import chegur.exception.WrongCredentialsException;
import chegur.service.impl.UserService;
import chegur.validator.CredentialsValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends BaseController {
    private final UserService userService = UserService.getInstance();
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
        try {
            userService.logIn(username, password);
        } catch (WrongCredentialsException e) {

        }

        processTemplate("home", context, resp);

    }
}
