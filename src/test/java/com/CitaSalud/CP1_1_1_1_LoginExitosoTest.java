package com.CitaSalud;

import com.CitaSalud.core.services.AuthService;
import com.CitaSalud.domain.entities.Rol;
import com.CitaSalud.domain.entities.Usuario;
import com.CitaSalud.domain.repository.UsuarioRepository;
import com.CitaSalud.dto.AuthResponse;
import com.CitaSalud.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CP1_1_1_1_LoginExitosoTest {

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
    void loginExitoso() {
        Rol rol = new Rol();
        rol.setIdRol(1L);
        rol.setNombreRol("ADMIN");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setEmail("jhon.garciaptest@udea.edu.co");
        usuario.setContrasena("1234");
        usuario.setIdRol(rol);

        when(usuarioRepository.findByEmailWithRol("jhon.garciaptest@udea.edu.co"))
                .thenReturn(Optional.of(usuario));
        when(jwtTokenProvider.generateToken(1L, "ADMIN")).thenReturn("TOKEN123");

        AuthResponse response = authService.authenticateUser("jhon.garciaptest@udea.edu.co", "1234");

        assertNotNull(response);
        assertEquals("TOKEN123", response.getToken());
    }
}
