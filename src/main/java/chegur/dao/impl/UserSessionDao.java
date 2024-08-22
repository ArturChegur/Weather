package chegur.dao.impl;

import chegur.dao.Dao;
import chegur.entity.UserSession;
import chegur.exception.SessionException;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSessionDao implements Dao<UserSession> {
    private static final UserSessionDao INSTANCE = new UserSessionDao();

    @Override
    public void save(UserSession userSession) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(userSession);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new SessionException();
        }
    }

    public void deleteSession(String guid) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<?> query = session.createQuery("delete from UserSession where guid = :guid");
            query.setParameter("guid", guid);

            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new SessionException();
        }
    }

    public Optional<UserSession> getSession(UserSession userSession) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<UserSession> query = session.createQuery("from UserSession where guid = :guid", UserSession.class);
            query.setParameter("guid", userSession.getGuid());
            return query.uniqueResultOptional();
        } catch (HibernateException e) {
            throw new SessionException();
        }
    }

    public static UserSessionDao getInstance() {
        return INSTANCE;
    }
}
