package chegur.controller.page;

import chegur.controller.BaseController;
import chegur.dto.UserSessionDto;
import chegur.service.AuthenticationService;
import chegur.service.SessionService;
import chegur.util.CookieHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/home")
public class HomeController extends BaseController {
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    private final SessionService sessionService = SessionService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = createWebContext(req, resp);

        Optional<String> guid = CookieHandler.getSessionCookie(req);

        if (guid.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UserSessionDto userSessionDto = sessionService.getSessionData(guid.get());

        if (userSessionDto == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        context.setVariable("username", userSessionDto.getUserDto().getLogin());

        processTemplate("home", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        Optional<String> guid = CookieHandler.getSessionCookie(req);

        if (guid.isEmpty()) {
            resp.sendRedirect("login");

            return;
        }

        if (action.equals("logout")) {
            authenticationService.logOut(guid.get());
            resp.sendRedirect("login");

            return;
        }

        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
    }
}
