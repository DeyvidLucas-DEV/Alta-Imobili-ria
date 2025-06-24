package alta.imobiliaria.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import alta.imobiliaria.domain.Agency;

@Data
@Entity
@FilterDef(name = "agencyFilter", parameters = @ParamDef(name = "agencyId", type = Long.class))
@Filter(name = "agencyFilter", condition = "agency_id = :agencyId")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private BigDecimal price;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;
}
