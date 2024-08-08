package chegur.dto;

import chegur.entity.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserDto {
    private String login;
    private Set<Location> locations;
}
