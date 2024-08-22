package chegur.service;

import chegur.dao.impl.UserDao;
import chegur.entity.User;
import chegur.exception.CredentialsException;
import chegur.exception.UserExistsException;
import chegur.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationService {
    private static final AuthenticationService INSTANCE = new AuthenticationService();

    private final UserDao userDao = UserDao.getInstance();
    private final SessionService sessionService = SessionService.getInstance();

    public String logIn(String username, String password) {
        User user = userDao.getUser(username).orElseThrow(() -> new UserNotFoundException("User was not found"));

        String hashedPassword = hashPassword(password);

        if (user.getPassword().equals(hashedPassword)) {
            return sessionService.startSession(user);
        } else {
            throw new CredentialsException("Password in incorrect");
        }
    }

    public void register(String login, String password) {
        try {
            userDao.save(buildUser(login, password));
        } catch (HibernateException e) {
            throw new UserExistsException("User with this username already exists");
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
