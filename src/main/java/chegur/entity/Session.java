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
public class Session {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String guid;

    @ManyToOne
    @JoinColumn(name = "client")
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiresAt;
}
