package chegur.dao.impl;

import chegur.dao.Dao;
import chegur.entity.Location;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDao implements Dao<Location> {
    private static final LocationDao INSTANCE = new LocationDao();

    @Override
    public void save(Location location) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(location);
            session.getTransaction().commit();
        }
    }

    public List<Location> getLocation(Location location) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Location> query = session.createQuery("from Location where name = :name", Location.class);
            query.setParameter("name", location.getName());
            return query.getResultList();
        }
    }

    public static LocationDao getInstance() {
        return INSTANCE;
    }
}
