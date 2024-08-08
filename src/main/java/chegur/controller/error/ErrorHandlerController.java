package chegur.controller.error;

import chegur.controller.BaseController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/error-handler")
public class ErrorHandlerController extends BaseController {
    private static final String HOME_URI = "/Weather/home";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");
        String errorMessage = (String) req.getAttribute("jakarta.servlet.error.message");

        WebContext context = createWebContext(req, resp);

        context.setVariable("statusCode", statusCode);
        context.setVariable("errorMessage", errorMessage);

        if (statusCode == 401) {
            resp.sendRedirect("login");
            return;
        }

        context.setVariable("redirectUrl", HOME_URI);
        context.setVariable("statusCode", statusCode);
        context.setVariable("errorMessage", errorMessage);

        processTemplate("error", context, resp);
    }
}
