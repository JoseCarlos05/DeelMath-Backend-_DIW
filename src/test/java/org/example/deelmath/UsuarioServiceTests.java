package org.example.deelmath;

import org.example.deelmath.dto.UsuarioInicioDTO;
import org.example.deelmath.modelos.Usuario;
import org.example.deelmath.repository.IUsuarioRepository;
import org.example.deelmath.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class UsuarioServiceTests {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    Usuario usuario = new Usuario();

    @BeforeEach
    public void inicializarBaseDatos() {

        usuario.setNombre("Paco");
        usuario.setEmail("paco123@gmail.com");
        usuario.setTelefono("601929384");
        usuario.setContrasena("123456");
        usuario.setFechaNacimiento(LocalDate.parse("2002-04-12"));

        usuarioRepository.save(usuario);
    }

    @Test
    @DisplayName("Test 1 --> Buscar un usuario por ID")
    @Tag("Usuario")
    public void testBuscarUsuarioIDPositivo() {

        UsuarioInicioDTO u = usuarioService.buscarUsuarioID(usuario.getId());

        assertNotNull(u);
        assertEquals(usuario.getNombre(), u.getNombre());
        assertEquals(usuario.getEmail(), u.getEmail());

    }

    @Test
    @DisplayName("Test 2 --> Buscar un usuario por un ID inexistente")
    @Tag("Usuario")
    public void testBuscarUsuarioIDNegativo() {

       assertThrows(Exception.class, () -> usuarioService.buscarUsuarioID(345));

    }

    @Test
    @DisplayName("Test 3 --> Buscar un usuario por un correo y contraseña")
    @Tag("Usuario")
    public void testBuscarUsuarioECPositivo() {

        UsuarioInicioDTO u = usuarioService.buscarUsuarioEC(usuario.getEmail(), usuario.getContrasena());

        assertNotNull(u);
        assertEquals(usuario.getNombre(), u.getNombre());
        assertEquals(usuario.getEmail(), u.getEmail());

    }

    @Test
    @DisplayName("Test 4 --> Buscar un usuario por un email inexistente")
    @Tag("Usuario")
    public void testBuscarUsuarioECNegativoEmail() {

        assertThrows(Exception.class, () -> usuarioService.buscarUsuarioEC("sdrgrddh", usuario.getContrasena()));

    }

    @Test
    @DisplayName("Test 5 --> Buscar un usuario por una contraseña inexistente")
    @Tag("Usuario")
    public void testBuscarUsuarioECNegativoContrasena() {

        assertThrows(Exception.class, () -> usuarioService.buscarUsuarioEC(usuario.getEmail(), "dfrgdrghgr"));

    }

}
