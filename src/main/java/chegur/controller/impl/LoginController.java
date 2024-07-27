package chegur.controller.impl;

import chegur.controller.BaseController;
import chegur.service.impl.UserService;
import chegur.validator.CredentialsValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends BaseController {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = createWebContext(req, resp);
        processTemplate("login", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String errorMessage = CredentialsValidator.isDataValid(username, password);
        WebContext context = createWebContext(req, resp);

        if (errorMessage != null) {
            context.setVariable("errorMessage", errorMessage);
            processTemplate("register", context, resp);
        }

    }
}
