package chegur.service;

import chegur.dao.UserDao;
import chegur.dto.UserDto;
import chegur.entity.User;
import chegur.mapper.UserMapper;
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

    public Optional<UserDto> login(String login, String password) {
        return userDao.findUser(buildUser(login, password)).map(userMapper::mapFrom);
    }

    public void addUser(String login, String password) {
        userDao.saveUser(buildUser(login, password));
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
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
