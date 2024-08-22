package chegur.dao.impl;

import chegur.dao.Dao;
import chegur.entity.Location;
import chegur.entity.User;
import chegur.exception.LocationException;
import chegur.exception.UserExistsException;
import chegur.exception.UserNotFoundException;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Objects;
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
            throw new UserExistsException("User with this usernmame already exists");
        }
    }

    public Optional<User> getUser(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<User> query = session.createQuery("from User where login = :login", User.class);
            query.setParameter("login", username);

            return query.uniqueResultOptional();
        } catch (HibernateException e) {
            throw new UserNotFoundException("User was not found");
        }
    }

    public void addFavouriteLocation(User user, Location location) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            user.getLocations().add(location);
            session.update(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new LocationException();
        }
    }

    public void deleteFavouriteLocation(Integer userId, Integer locationId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            User user = session.find(User.class, userId);
            Location location = session.find(Location.class, locationId);

            if (Objects.equals(location.getAuthor().getId(), userId)) {
                user.getLocations().remove(location);
                session.remove(location);
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            throw new LocationException();
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}