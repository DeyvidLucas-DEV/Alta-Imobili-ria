package alta.imobiliaria.config;

import alta.imobiliaria.domain.Agency;
import alta.imobiliaria.repository.AgencyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AgencyFilter extends OncePerRequestFilter {
    private final EntityManager entityManager;
    private final AgencyRepository agencyRepository;

    public AgencyFilter(EntityManager entityManager, AgencyRepository agencyRepository) {
        this.entityManager = entityManager;
        this.agencyRepository = agencyRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Session session = entityManager.unwrap(Session.class);
        try {
            String domain = request.getHeader("X-Agency-Domain");
            if (domain == null || domain.isBlank()) {
                domain = request.getServerName();
            }
            Agency agency = agencyRepository.findByDomain(domain).orElse(null);
            if (agency != null) {
                session.enableFilter("agencyFilter").setParameter("agencyId", agency.getId());
            }
            filterChain.doFilter(request, response);
        } finally {
            session.disableFilter("agencyFilter");
        }
    }
}
