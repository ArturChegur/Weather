package chegur.dao;

import chegur.entity.UserSession;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionDao {
    public static final SessionDao INSTANCE = new SessionDao();

    public void saveSession(UserSession currentUserSession) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(currentUserSession);
            session.getTransaction().commit();
        }
    }


    public static SessionDao getInstance() {
        return INSTANCE;
    }
}
