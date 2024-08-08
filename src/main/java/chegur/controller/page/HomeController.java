package chegur.controller.page;

import chegur.controller.BaseController;
import chegur.service.AuthenticationService;
import chegur.util.CookieHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/home")
public class HomeController extends BaseController {
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = createWebContext(req, resp);

        processTemplate("home", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        Cookie[] cookies = req.getCookies();
        Optional<String> guid = CookieHandler.getSessionCookie(cookies);

        if (guid.isEmpty()) {
            resp.sendRedirect("login");
            return;
        }

        if (action.equals("search")) {
            return;
        }

        if (action.equals("logout")) {
            handleLogout(guid.get());
            resp.sendRedirect("login");
            return;
        }

        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
    }

    private void handleLogout(String guid) {
        authenticationService.logOut(guid);
    }

    private void handleSearch() {

    }
}
