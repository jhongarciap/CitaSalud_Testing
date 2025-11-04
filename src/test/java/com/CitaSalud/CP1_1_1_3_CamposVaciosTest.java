package com.CitaSalud;

import com.CitaSalud.core.services.AuthService;
import com.CitaSalud.domain.repository.UsuarioRepository;
import com.CitaSalud.exceptions.BadCredentialsException;
import com.CitaSalud.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CP1_1_1_3_CamposVaciosTest {

    private AuthService authService;
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        authService = new AuthService(usuarioRepository, passwordEncoder, jwtTokenProvider);
    }

    @Test
    void camposVacios() {
        BadCredentialsException ex1 = assertThrows(BadCredentialsException.class,
                () -> authService.authenticateUser("", "1234"));
        BadCredentialsException ex2 = assertThrows(BadCredentialsException.class,
                () -> authService.authenticateUser("jhon.garciaptest@udea.edu.co", ""));
        BadCredentialsException ex3 = assertThrows(BadCredentialsException.class,
                () -> authService.authenticateUser("", ""));

        assertEquals("Credenciales inválidas.", ex1.getMessage());
        assertEquals("Credenciales inválidas.", ex2.getMessage());
        assertEquals("Credenciales inválidas.", ex3.getMessage());
    }
}
