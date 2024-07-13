package chegur.controller;

import chegur.util.ThymeleafConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

public abstract class BaseController extends HttpServlet {
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        this.templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
    }

    protected WebContext createWebContext(HttpServletRequest req, HttpServletResponse resp) {
        return ThymeleafConfig.buildWebContext(req, resp, getServletContext());
    }

    protected void processTemplate(String templateName, WebContext context, HttpServletResponse resp) throws IOException {
        templateEngine.process(templateName, context, resp.getWriter());
    }
}
