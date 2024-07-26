package chegur.service;

import chegur.dao.UserSessionDao;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionService {
    public static final SessionService INSTANCE = new SessionService();

    private final UserSessionDao userSessionDao = UserSessionDao.getInstance();

    public void addSession() {


    }

    public static SessionService getInstance() {
        return INSTANCE;
    }
}
