package org.example.deelmath;

import org.example.deelmath.modelos.Amistad;
import org.example.deelmath.modelos.Usuario;
import org.example.deelmath.repository.IAmistadRepository;
import org.example.deelmath.repository.IUsuarioRepository;
import org.example.deelmath.service.AmistadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class AmistadServiceIntegrationTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IAmistadRepository amistadRepository;

    @InjectMocks
    private AmistadService amistadService;

    @Test
    @DisplayName("Test --> Ver los amigos de un usuario que no exista")
    @Tag("Amigos")
    public void testListarAmigosNegativo() {

        //GIVEN

        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();
        Amistad amigos1 = new Amistad();
        Amistad amigos2 = new Amistad();

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

        when(amistadRepository.save(amigos1)).thenReturn(amigos1);
        when(amistadRepository.save(amigos2)).thenReturn(amigos2);

        Set<Amistad> amigosp1 = new HashSet<>();
        amigosp1.add(amigos1);

        Set<Amistad> amigosp2 = new HashSet<>();
        amigosp2.add(amigos2);

        usuario1.setAmigos(amigosp1);
        usuario2.setAmigos(amigosp2);

        when(usuarioRepository.save(usuario1)).thenReturn(usuario1);
        when(usuarioRepository.save(usuario2)).thenReturn(usuario2);

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        //WHEN && THEN
        assertThrows(Exception.class, () -> amistadService.listarAmigos(234));
    }
}
