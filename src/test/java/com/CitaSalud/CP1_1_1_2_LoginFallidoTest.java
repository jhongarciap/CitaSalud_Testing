package com.CitaSalud;

import com.CitaSalud.core.services.AuthService;
import com.CitaSalud.domain.repository.UsuarioRepository;
import com.CitaSalud.exceptions.BadCredentialsException;
import com.CitaSalud.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CP1_1_1_2_LoginFallidoTest {

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
    void loginFallido() {
        when(usuarioRepository.findByEmailWithRol("usuario.incorrecto@test.com"))
                .thenReturn(Optional.empty());

        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authService.authenticateUser("usuario.incorrecto@test.com", "1234"));

        assertEquals("Credenciales inválidas.", exception.getMessage());
    }
}
