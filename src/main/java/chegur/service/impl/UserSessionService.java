package chegur.service.impl;

import chegur.dao.impl.UserSessionDao;
import chegur.dto.UserSessionDto;
import chegur.entity.UserSession;
import chegur.mapper.impl.UserSessionMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSessionService {
    public static final UserSessionService INSTANCE = new UserSessionService();
    private final UserSessionDao userSessionDao = UserSessionDao.getInstance();
    private final UserSessionMapper userSessionMapper = UserSessionMapper.getInstance();

    public void addSession() {
        //todo add session when logged in
    }

    public UserSessionDto getUserSession(String guid) {
        Optional<UserSession> userSession = userSessionDao.getSession(buildUserSession(guid));
        return userSession.map(userSessionMapper::mapFrom).orElse(null);
    }

    private UserSession buildUserSession(String guid) {
        return UserSession.builder()
                .guid(guid)
                .build();
    }

    public static UserSessionService getInstance() {
        return INSTANCE;
    }
}
