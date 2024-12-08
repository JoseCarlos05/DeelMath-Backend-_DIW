package org.example.deelmath;

import org.example.deelmath.dto.UsuarioInicioDTO;
import org.example.deelmath.modelos.Amistad;
import org.example.deelmath.modelos.Grupo;
import org.example.deelmath.modelos.Usuario;
import org.example.deelmath.repository.IAmistadRepository;
import org.example.deelmath.repository.IUsuarioRepository;
import org.example.deelmath.service.AmistadService;
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
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AmistadServiceTests {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private IAmistadRepository amistadRepository;

    @Autowired
    private AmistadService amistadService;

    Usuario usuario1 = new Usuario();
    Usuario usuario2 = new Usuario();
    Amistad amigos1 = new Amistad();
    Amistad amigos2 = new Amistad();

    @BeforeEach
    public void inicializarBaseDatos() {

        usuario1.setNombre("Paco");
        usuario1.setEmail("paco123@gmail.com");
        usuario1.setTelefono("601929384");
        usuario1.setContrasena("123456");
        usuario1.setFechaNacimiento(LocalDate.parse("2002-04-12"));

        usuario2.setNombre("Antonio");
        usuario2.setEmail("antonio123@gmail.com");
        usuario2.setTelefono("601243565");
        usuario2.setContrasena("123456");
        usuario2.setFechaNacimiento(LocalDate.parse("2002-05-25"));

        amigos1.setUsuario(usuario1);
        amigos1.setAmigo(usuario2);
        amigos2.setUsuario(usuario2);
        amigos2.setAmigo(usuario1);

        amistadRepository.save(amigos1);
        amistadRepository.save(amigos2);

        Set<Amistad> amigosp1 = new HashSet<>();
        amigosp1.add(amigos1);

        Set<Amistad> amigosp2 = new HashSet<>();
        amigosp2.add(amigos2);

        usuario1.setAmigos(amigosp1);
        usuario2.setAmigos(amigosp2);

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
    }


    @Test
    @DisplayName("Test 1 --> Ver los amigos de un usuario")
    @Tag("Amigos")
    public void testListarAmigosPositivo() {

        List<UsuarioInicioDTO> amigos = amistadService.listarAmigos(usuario1.getId());

        assertEquals(amigos.getFirst().getId(), usuario2.getId());
    }

    @Test
    @DisplayName("Test 2 --> Ver los amigos de un usuario que no exista")
    @Tag("Amigos")
    public void testListarAmigosNegativo() {

        assertThrows(Exception.class, () -> amistadService.listarAmigos(234));

    }
}
