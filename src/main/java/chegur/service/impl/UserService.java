package chegur.service.impl;

import chegur.dao.impl.UserDao;
import chegur.dto.UserDto;
import chegur.entity.User;
import chegur.exception.WrongCredentialsException;
import chegur.mapper.impl.UserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public UserDto logIn(String login, String password) {
        Optional<User> user = userDao.getUser(buildUser(login, password));
        if (user.isPresent()) {
            return userMapper.mapFrom(user.get());
        } else {
            throw new WrongCredentialsException("Wrong username or password");
        }
    }

    public void addUser(String login, String password) {
        //userDao.saveUser(buildUser(login, password));
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
                .password(password)
                .build();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
