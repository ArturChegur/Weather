package chegur.service;

import chegur.dao.impl.UserDao;
import chegur.entity.User;
import chegur.exception.UserExistsException;
import chegur.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationService {
    private static final AuthenticationService INSTANCE = new AuthenticationService();
    private final UserDao userDao = UserDao.getInstance();
    private final SessionService sessionService = SessionService.getInstance();

    public String logIn(String login, String password, String guid) {
        Optional<User> user = userDao.getUser(buildUser(login, hashPassword(password)));

        if (user.isPresent()) {
            return sessionService.startSession(user.get(), guid);
        } else {
            throw new UserNotFoundException();
        }
    }

    public void register(String login, String password) {
        try {
            userDao.save(buildUser(login, hashPassword(password)));
        } catch (UserExistsException e) {
            throw new UserNotFoundException();
        }
    }

    public void logOut(String guid) {
        sessionService.invalidateSession(guid);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private User buildUser(String login, String password) {
        return User.builder()
                .login(login)
                .password(hashPassword(password))
                .build();
    }

    public static AuthenticationService getInstance() {
        return INSTANCE;
    }
}
