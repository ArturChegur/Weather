package chegur.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "locations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"author", "latitude", "longitude"})
})
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @Column(name = "latitude", nullable = false, precision = 6, scale = 3)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 6, scale = 3)
    private BigDecimal longitude;
}
