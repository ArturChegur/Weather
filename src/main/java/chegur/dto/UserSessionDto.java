package chegur.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSessionDto {
    private String guid;
    private UserDto userDto;
    private boolean isExpired;
}
