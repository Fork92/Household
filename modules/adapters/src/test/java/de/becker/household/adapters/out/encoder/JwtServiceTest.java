package de.becker.household.adapters.out.encoder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;

    @Test
    public void testGenerateJwt() {
        jwtService = new JwtService();
        Map<String, Object> claims = new HashMap<>();
        String token = jwtService.generateToken(claims, "Test");

        assertThat(token).isNotNull();
        assertThat(jwtService.parseToken(token).getSubject()).isEqualTo("Test");
        assertThat(jwtService.validateToken(token)).isTrue();
    }

}
