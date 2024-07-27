package chegur.dto;

import chegur.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSessionDto {
    private String guid;
    private User user;
    private boolean isExpired;
}
