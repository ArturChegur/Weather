package chegur.service;

import chegur.dao.SessionDao;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionService {
    public static final SessionService INSTANCE = new SessionService();

    private final SessionDao sessionDao = SessionDao.getInstance();

    public void addSession() {


    }

    public static SessionService getInstance() {
        return INSTANCE;
    }
}
