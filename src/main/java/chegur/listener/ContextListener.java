package chegur.listener;

import chegur.util.ThymeleafUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.thymeleaf.TemplateEngine;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        TemplateEngine templateEngine = ThymeleafUtil.buildTemplateEngine(servletContext);

        String dbUrl = System.getenv("DATABASE_URL");
        String dbUser = System.getenv("DATABASE_USER");
        String dbPassword = System.getenv("DATABASE_PASSWORD");

        Flyway flyway = Flyway.configure()
                .dataSource(dbUrl, dbUser, dbPassword)
                .locations("classpath:db")
                .load();

        try {
            if (!flyway.validateWithResult().validationSuccessful) {
                flyway.migrate();
            }
        } catch (FlywayException ignored) {
        }

        sce.getServletContext().setAttribute("templateEngine", templateEngine);
    }

}
