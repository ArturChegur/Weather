package chegur.dao.impl;

import chegur.dao.Dao;
import chegur.entity.Location;
import chegur.exception.LocationException;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDao implements Dao<Location> {
    private static final LocationDao INSTANCE = new LocationDao();

    @Override
    public void save(Location location) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(location);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new LocationException();
        }
    }

    public static LocationDao getInstance() {
        return INSTANCE;
    }
}
