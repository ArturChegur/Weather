package chegur.dao;

import chegur.entity.UserSession;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSessionDao {
    private static final UserSessionDao INSTANCE = new UserSessionDao();

    public void saveSession(UserSession currentUserSession) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(currentUserSession);
            session.getTransaction().commit();
        }
    }

    public Optional<UserSession> getSession(UserSession userSession) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<UserSession> query = session.createQuery("from UserSession where guid = :guid", UserSession.class);
            query.setParameter("guid", userSession.getGuid());
            return query.uniqueResultOptional();
        }
    }

    public static UserSessionDao getInstance() {
        return INSTANCE;
    }
}
