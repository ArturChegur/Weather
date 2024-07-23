package chegur.dao;

import chegur.entity.User;
import chegur.exception.UserExistsException;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {
    private static final UserDao INSTANCE = new UserDao();

    public Optional<User> findUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where login = :login and password = :password", User.class);
            query.setParameter("login", user.getLogin());
            query.setParameter("password", user.getPassword());
            return query.uniqueResultOptional();
        }
    }

    public void saveUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new UserExistsException("User with this username already exists");
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
