package chegur.dao;

import chegur.entity.Location;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDao {
    private static final LocationDao INSTANCE = new LocationDao();

    public void saveLocation(Location location) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(location);
            session.getTransaction().commit();
        }
    }

    public Optional<List<Location>> getLocation(Location location) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Location> query = session.createQuery("from Location where name = :name", Location.class);
            query.setParameter("name", location.getName());
            //todo
        }
    }

    public static LocationDao getInstance() {
        return INSTANCE;
    }
}
