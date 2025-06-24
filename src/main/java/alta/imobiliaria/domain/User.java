package alta.imobiliaria.domain;

import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.Filter;

import alta.imobiliaria.domain.Agency;

@Data
@Entity
@Table(name = "users")
@Filter(name = "agencyFilter", condition = "agency_id = :agencyId")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;
}
