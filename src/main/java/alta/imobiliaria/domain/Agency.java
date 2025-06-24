package alta.imobiliaria.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Data
@Entity
@Table(name = "agencies")
@FilterDef(name = "agencyFilter", parameters = @ParamDef(name = "agencyId", type = Long.class))
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String domain;
}
