package chegur.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessions")
public class UserSession {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String guid;

    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    private User user;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
}
