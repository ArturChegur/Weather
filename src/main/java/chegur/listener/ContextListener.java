package chegur.listener;

import chegur.util.ThymeleafUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;
import org.thymeleaf.TemplateEngine;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        TemplateEngine templateEngine = ThymeleafUtil.buildTemplateEngine(servletContext);

        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";//System.getenv("DB_URL");
        String dbUser = "artur";//System.getenv("DB_USER");
        String dbPassword = "chegur";//System.getenv("DB_PASSWORD");

        Flyway flyway = Flyway.configure()
                .dataSource(dbUrl, dbUser, dbPassword)
                .locations("classpath:db")
                .load();

        flyway.migrate();

        sce.getServletContext().setAttribute("templateEngine", templateEngine);
    }
}
