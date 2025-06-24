package alta.imobiliaria.repository;

import alta.imobiliaria.domain.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
    Optional<Agency> findByDomain(String domain);
}
