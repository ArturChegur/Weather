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
        WebContext context = createWebContext(req, resp);

        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");
        String statusErrorMessage = (String) req.getAttribute("jakarta.servlet.error.message");

        Throwable exception = (Throwable) req.getAttribute("javax.servlet.error.exception");
        String exceptionErrorMessage = exception != null ? exception.getMessage() : "Unknown error";

        if (exception != null) {
            processError(exceptionErrorMessage, 500, context, resp);
        }

        if (statusCode == 401) {
            resp.sendRedirect("login");
            return;
        }

        processError(statusErrorMessage, statusCode, context, resp);
    }

    private void processError(String error, Integer statusCode, WebContext context, HttpServletResponse resp) throws IOException {
        context.setVariable("errorMessage", error);
        context.setVariable("statusCode", statusCode);
        context.setVariable("redirectUrl", HOME_URI);

        processTemplate("error", context, resp);
    }
}
