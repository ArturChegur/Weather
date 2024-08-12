package chegur.service;

import chegur.dao.impl.UserSessionDao;
import chegur.dto.UserSessionDto;
import chegur.entity.User;
import chegur.entity.UserSession;
import chegur.exception.SessionNotFoundException;
import chegur.mapper.impl.UserSessionMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionService {
    public static final SessionService INSTANCE = new SessionService();
    private final UserSessionDao userSessionDao = UserSessionDao.getInstance();
    private final UserSessionMapper userSessionMapper = UserSessionMapper.getInstance();

    public String startSession(User user, String guid) {
        if (guid != null) {
            invalidateSession(guid);
        }

        return createSession(user);
    }

    public UserSessionDto getSessionData(String guid) {
        Optional<UserSession> userSession = userSessionDao.getSession(buildUserSession(guid, null));
        return userSession.map(userSessionMapper::mapFrom).orElse(null);
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
