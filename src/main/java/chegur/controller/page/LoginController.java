package chegur.controller.page;

import chegur.controller.BaseController;
import chegur.exception.CredentialsException;
import chegur.exception.UserNotFoundException;
import chegur.service.AuthenticationService;
import chegur.validator.CredentialsValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends BaseController {
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = createWebContext(req, resp);
        processTemplate("login", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        WebContext context = createWebContext(req, resp);

        try {
            CredentialsValidator.validateData(username, password);
            String sessionCookie = authenticationService.logIn(username, password);
            resp.addCookie(new Cookie("JSESSIONID", sessionCookie));
        } catch (CredentialsException | UserNotFoundException e) {
            processError(e.getMessage(), context, resp);
            return;
        }

        resp.sendRedirect("home");
    }

    private void processError(String error, WebContext context, HttpServletResponse resp) throws IOException {
        context.setVariable("errorMessage", error);
        processTemplate("login", context, resp);
    }
}
