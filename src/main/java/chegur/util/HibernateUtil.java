package chegur.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

@UtilityClass
public class HibernateUtil {
    private static final SessionFactory INSTANCE = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
            configuration.configure();

            configuration.setProperty(Environment.URL, System.getenv("DATABASE_URL"));
            configuration.setProperty(Environment.USER, System.getenv("DATABASE_USER"));
            configuration.setProperty(Environment.PASS, System.getenv("DATABASE_PASSWORD"));

            return configuration.buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return INSTANCE;
    }
}
