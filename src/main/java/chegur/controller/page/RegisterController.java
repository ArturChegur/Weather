package chegur.controller.page;

import chegur.controller.BaseController;
import chegur.exception.UserNotFoundException;
import chegur.service.AuthenticationService;
import chegur.validator.CredentialsValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends BaseController {
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

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
            processError(errorMessage, context, resp);
            return;
        }

        try {
            authenticationService.register(username, password);
        } catch (UserNotFoundException e) {
            processError("Username already taken", context, resp);
            return;
        }

        resp.sendRedirect("login");
    }

    private void processError(String error, WebContext context, HttpServletResponse resp) throws IOException {
        context.setVariable("errorMessage", error);
        processTemplate("register", context, resp);
    }
}
