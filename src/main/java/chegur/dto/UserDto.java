package chegur.dto;

import chegur.entity.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private String login;
    private List<Location> locations;
}
