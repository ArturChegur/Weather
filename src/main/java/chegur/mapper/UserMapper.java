package chegur.mapper;

import chegur.dto.UserDto;
import chegur.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<User, UserDto> {
    public static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .login(user.getLogin())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
