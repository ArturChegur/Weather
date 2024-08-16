package chegur.service;

import chegur.dao.impl.UserSessionDao;
import chegur.entity.User;
import chegur.entity.UserSession;
import chegur.exception.SessionNotFoundException;
import chegur.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionService {
    private static final SessionService INSTANCE = new SessionService();
    private final UserSessionDao userSessionDao = UserSessionDao.getInstance();

    public String startSession(User user) {
        return createSession(user);
    }

    public User getSessionUser(String guid) {
        Optional<UserSession> userSession = userSessionDao.getSession(buildUserSession(guid, null));

        if (userSession.isPresent()) {
            return userSession.get().getUser();
        } else {
            throw new UserNotFoundException();
        }
    }

    public boolean isSessionActive(String guid) {
        Optional<UserSession> userSession = userSessionDao.getSession(buildUserSession(guid, null));
        return userSession.map(session -> session.getExpiresAt().isAfter(LocalDateTime.now())).orElse(false);
    }

    public void invalidateSession(String guid) {
        try {
            userSessionDao.deleteSession(guid);
        } catch (SessionNotFoundException ignored) {
        }
    }

    private String createSession(User user) {
        String guid = UUID.randomUUID().toString();
        userSessionDao.save(buildUserSession(guid, user));

        return guid;
    }

    private UserSession buildUserSession(String guid, User user) {
        return UserSession.builder()
                .guid(guid)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(60))
                .build();
    }

    public static SessionService getInstance() {
        return INSTANCE;
    }
}
