package org.example.deelmath;

import org.example.deelmath.dto.UsuarioInicioDTO;
import org.example.deelmath.modelos.Usuario;
import org.example.deelmath.repository.IUsuarioRepository;
import org.example.deelmath.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceIntegrationTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Test --> Buscar un usuario por un correo y contraseña")
    @Tag("Usuario")
    public void testBuscarUsuarioECPositivo() {

        //GIVEN
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Paco");
        usuario.setEmail("paco123@gmail.com");
        usuario.setTelefono("601929384");
        usuario.setContrasena("123456");
        usuario.setFechaNacimiento(LocalDate.parse("2002-04-12"));

        when(usuarioRepository.findByEmailAndContrasena("paco123@gmail.com", "123456")).thenReturn(usuario);

        //WHEN
        UsuarioInicioDTO u = usuarioService.buscarUsuarioEC(usuario.getEmail(), usuario.getContrasena());

        //THEN
        assertNotNull(u);
        assertEquals(usuario.getNombre(), u.getNombre());
        assertEquals(usuario.getEmail(), u.getEmail());

        verify(usuarioRepository, times(1)).findByEmailAndContrasena(usuario.getEmail(), usuario.getContrasena());
    }
}
