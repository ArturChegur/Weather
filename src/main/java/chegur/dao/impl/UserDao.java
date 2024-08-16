package chegur.dao.impl;

import chegur.dao.Dao;
import chegur.entity.Location;
import chegur.entity.User;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
            session.update(user);
            session.getTransaction().commit();
        }
    }

    public void deleteFavouriteLocation(Integer userId, Integer locationId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            User user = session.find(User.class, userId);
            Location location = session.find(Location.class, locationId);

            user.getLocations().remove(location);
            session.remove(location);
            session.getTransaction().commit();
        } catch (Exception ignored) {
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}