package alta.imobiliaria;

import alta.imobiliaria.domain.User;
import alta.imobiliaria.domain.Agency;
import alta.imobiliaria.repository.UserRepository;
import alta.imobiliaria.repository.AgencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        agencyRepository.deleteAll();

        Agency agency = new Agency();
        agency.setName("Default");
        agency.setDomain("localhost");
        agency = agencyRepository.save(agency);

        User user = new User();
        user.setUsername("user");
        user.setPassword(encoder.encode("password"));
        user.setAgency(agency);
        repository.save(user);
    }

    @Test
    void requiresAuthentication() throws Exception {
        mvc.perform(get("/properties"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void validCredentialsAuthenticate() throws Exception {
        mvc.perform(get("/properties").with(httpBasic("user", "password")))
                .andExpect(status().isOk());
    }

    @Test
    void invalidCredentialsFail() throws Exception {
        mvc.perform(get("/properties").with(httpBasic("user", "wrong")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void canCreateUserWithoutAuthentication() throws Exception {
        Agency agency = agencyRepository.findAll().getFirst();
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("newpass");
        newUser.setAgency(agency);

        mvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser))
        ).andExpect(status().isCreated());
    }
}
