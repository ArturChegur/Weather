package chegur.controller.page;

import chegur.controller.BaseController;
import chegur.service.AuthenticationService;
import chegur.util.CookieHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/logout")
public class LogOutController extends BaseController {
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<String> guid = CookieHandler.getSessionCookie(req);

        if (guid.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        authenticationService.logOut(guid.get());
        resp.sendRedirect("login");
    }
}
