package alta.imobiliaria.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "agencies")
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String domain;
}
