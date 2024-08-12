package chegur.dao.impl;

import chegur.dao.Dao;
import chegur.entity.Location;
import chegur.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
        }
    }

    public static LocationDao getInstance() {
        return INSTANCE;
    }
}
