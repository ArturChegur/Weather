package chegur.mapper.impl;

import chegur.dto.UserSessionDto;
import chegur.entity.UserSession;
import chegur.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSessionMapper implements Mapper<UserSession, UserSessionDto> {
    private static final UserSessionMapper INSTANCE = new UserSessionMapper();
    private final UserMapper userMapper = UserMapper.getInstance();

    @Override
    public UserSessionDto mapFrom(UserSession userSession) {
        return UserSessionDto.builder()
                .guid(userSession.getGuid())
                .userDto(userMapper.mapFrom(userSession.getUser()))
                .isExpired(userSession.getExpiresAt().isBefore(LocalDateTime.now()))
                .build();
    }

    public static UserSessionMapper getInstance() {
        return INSTANCE;
    }
}
