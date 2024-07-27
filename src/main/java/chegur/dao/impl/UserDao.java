package chegur.dao.impl;

import chegur.dao.Dao;
import chegur.entity.Location;
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
public class UserDao implements Dao<User> {
    private static final UserDao INSTANCE = new UserDao();

    @Override
    public void save(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new UserExistsException("User with this username already exists");
        }
    }

    public Optional<User> getUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where login = :login and password = :password", User.class);
            query.setParameter("login", user.getLogin());
            query.setParameter("password", user.getPassword());
            return query.uniqueResultOptional();
        }
    }

    public void addFavouriteLocation(User user, Location location) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            user.getLocations().add(location);
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        }
    }

    public void deleteFavouriteLocation(User user, Location location) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            user.getLocations().remove(location);
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
