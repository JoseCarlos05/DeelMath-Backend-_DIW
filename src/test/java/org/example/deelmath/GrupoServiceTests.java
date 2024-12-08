package org.example.deelmath;

import org.example.deelmath.dto.GrupoDTO;
import org.example.deelmath.dto.UsuarioDTO;
import org.example.deelmath.modelos.Grupo;
import org.example.deelmath.modelos.Moneda;
import org.example.deelmath.modelos.Usuario;
import org.example.deelmath.repository.IGrupoRepository;
import org.example.deelmath.repository.IUsuarioRepository;
import org.example.deelmath.service.GrupoService;
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
class GrupoServiceTests {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private IGrupoRepository grupoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    Usuario usuario1 = new Usuario();
    Usuario usuario2 = new Usuario();
    Grupo grupo1 = new Grupo();

    @BeforeEach
    public void inicializarBaseDatos() {

        usuario1.setNombre("Paco");
        usuario1.setEmail("paco123@gmail.com");
        usuario1.setTelefono("601929384");
        usuario1.setContrasena("123456");
        usuario1.setFechaNacimiento(LocalDate.parse("2002-04-12"));
        usuario1.setGrupos(new HashSet<>());
        usuario1.setGastos(new HashSet<>());
        usuario1.setAmigos(new HashSet<>());
        usuario1.setBalances(new HashSet<>());

        usuarioRepository.save(usuario1);


        usuario2.setNombre("Antonio");
        usuario2.setEmail("antonio123@gmail.com");
        usuario2.setTelefono("601243565");
        usuario2.setContrasena("123456");
        usuario2.setFechaNacimiento(LocalDate.parse("2002-05-25"));

        grupo1.setNombre("Piso en Sevilla");
        grupo1.setMoneda(Moneda.EUR);

        Set<Grupo> grupos = new HashSet<>();
        grupos.add(grupo1);
        usuario2.setGrupos(grupos);

        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(usuario2);
        grupo1.setUsuarios(usuarios);
        grupo1.setCreador(usuario2);

        usuarioRepository.save(usuario2);
        grupoRepository.save(grupo1);
    }


    @Test
    @DisplayName("Test 1 --> Creación de grupo")
    @Tag("Grupo")
    public void testCrearGrupoPositivo() throws Exception {

        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setNombre("Piso en Madrid");
        grupoDTO.setMoneda(Moneda.EUR);
        Set<Integer> usuarios = new HashSet<>();
        usuarios.add(usuario1.getId());
        grupoDTO.setUsuarios(usuarios);
        grupoDTO.setGastos(new HashSet<>());
        grupoDTO.setBalances(new HashSet<>());
        grupoDTO.setCreador(usuario1.getId());

        GrupoDTO grupo = grupoService.crearGrupo(grupoDTO);

        assertNotNull(grupo);
        assertEquals(grupo.getNombre(), grupoDTO.getNombre());
    }

    @Test
    @DisplayName("Test 2 --> Creación de grupo con moneda inexistente")
    @Tag("Grupo")
    public void testCrearGrupoNegativoMoneda() {

        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setNombre("Piso en Madrid");
        try {
            grupoDTO.setMoneda(Moneda.valueOf("dolar"));
        } catch (IllegalArgumentException e) {
            grupoDTO.setMoneda(null);
        }
        Set<Integer> usuarios = new HashSet<>();
        usuarios.add(usuario1.getId());
        grupoDTO.setUsuarios(usuarios);
        grupoDTO.setGastos(new HashSet<>());
        grupoDTO.setBalances(new HashSet<>());
        grupoDTO.setCreador(usuario1.getId());

        assertThrows(IllegalArgumentException.class, () -> grupoService.crearGrupo(grupoDTO));
    }

    @Test
    @DisplayName("Test 3 --> Creación de grupo con ID de usuario inexistente")
    @Tag("Grupo")
    public void testCrearGrupoNegativoID() {

        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setNombre("Piso en Madrid");
        grupoDTO.setMoneda(Moneda.EUR);
        Set<Integer> usuarios = new HashSet<>();
        usuarios.add(213);
        grupoDTO.setUsuarios(usuarios);
        grupoDTO.setGastos(new HashSet<>());
        grupoDTO.setBalances(new HashSet<>());
        grupoDTO.setCreador(213);

        assertThrows(Exception.class, () -> grupoService.crearGrupo(grupoDTO));
    }

    @Test
    @DisplayName("Test 4 --> Creación de grupo sin ID del creador")
    @Tag("Grupo")
    public void testCrearGrupoNegativoSinCreador() {

        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setNombre("Piso en Madrid");
        grupoDTO.setMoneda(Moneda.EUR);
        Set<Integer> usuarios = new HashSet<>();
        usuarios.add(usuario1.getId());
        grupoDTO.setUsuarios(usuarios);

        assertThrows(Exception.class, () -> grupoService.crearGrupo(grupoDTO));
    }

    @Test
    @DisplayName("Test 5 --> Creación de un grupo donde el ID del usuario creador no esté de primeras en los usuarios del grupo")
    @Tag("Grupo")
    public void testCrearGrupoNegativoCreadorDistinto() {

        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setNombre("Piso en Madrid");
        grupoDTO.setMoneda(Moneda.EUR);
        Set<Integer> usuarios = new HashSet<>();
        usuarios.add(usuario1.getId());
        grupoDTO.setUsuarios(usuarios);
        grupoDTO.setCreador(usuario2.getId());

        assertThrows(Exception.class, () -> grupoService.crearGrupo(grupoDTO));
    }

    @Test
    @DisplayName("Test 6 --> Añadir participante a un grupo")
    @Tag("Grupo")
    public void testAnyadirParticipantePositivo() {

        grupoService.anyadirParticipantesGrupo(grupo1.getId(), usuario1.getId());

        assertTrue(grupo1.getUsuarios().contains(usuario1));

    }

    @Test
    @DisplayName("Test 7 --> Añadir participante que no exista a un grupo")
    @Tag("Grupo")
    public void testAnyadirParticipanteNegativo() {

        assertThrows(Exception.class, () -> grupoService.anyadirParticipantesGrupo(grupo1.getId(), 234));

    }

    @Test
    @DisplayName("Test 8 --> Añadir participante a un grupo que no exista")
    @Tag("Grupo")
    public void testAnyadirParticipanteNegativoGrupo() {

        assertThrows(Exception.class, () -> grupoService.anyadirParticipantesGrupo(324, usuario1.getId()));

    }

    @Test
    @DisplayName("Test 9 --> Añadir participante que ya esté en el grupo al que se añade")
    @Tag("Grupo")
    public void testAnyadirParticipanteNegativoExistente() {

        assertEquals(1, grupo1.getUsuarios().size());

        grupoService.anyadirParticipantesGrupo(grupo1.getId(), usuario2.getId());

        assertEquals(1, grupo1.getUsuarios().size());

    }

    @Test
    @DisplayName("Test 10 --> Listar participantes de un grupo")
    @Tag("Grupo")
    public void testVerParticipantesGrupoPositivo() {

        List<UsuarioDTO> usuarios = grupoService.verParticipantesGrupo(grupo1.getId());

        assertEquals(1, usuarios.size());
        assertSame(usuarios.getFirst().getId(), usuario2.getId());

    }

    @Test
    @DisplayName("Test 11 --> Listar participantes de un grupo que no exista")
    @Tag("Grupo")
    public void testVerParticipantesGrupoNegativo() {

        assertThrows(Exception.class, () -> grupoService.verParticipantesGrupo(324));

    }

    @Test
    @DisplayName("Test 12 --> Eliminar un participante de un grupo")
    @Tag("Grupo")
    public void testEliminarParticipanteGrupoPositivo() {

        grupoService.eliminarParticipantesGrupo(grupo1.getId(), usuario2.getId());

        assertFalse(grupo1.getUsuarios().contains(usuario2));

    }

    @Test
    @DisplayName("Test 13 --> Eliminar un participante que no exista de un grupo")
    @Tag("Grupo")
    public void testEliminarParticipanteGrupoNegativo() {

        assertThrows(Exception.class, () -> grupoService.eliminarParticipantesGrupo(grupo1.getId(), 567));

    }

    @Test
    @DisplayName("Test 14 --> Eliminar un participante de un grupo que no exista")
    @Tag("Grupo")
    public void testEliminarParticipanteGrupoNegativoGrupo() {

        assertThrows(Exception.class, () -> grupoService.eliminarParticipantesGrupo(987, usuario2.getId()));

    }

    @Test
    @DisplayName("Test 15 --> Eliminar un participante de un grupo en el cual no esté")
    @Tag("Grupo")
    public void testEliminarParticipanteGrupoNegativoUsuario() {

        assertEquals(1, grupo1.getUsuarios().size());
        assertFalse(grupo1.getUsuarios().contains(usuario1));

        grupoService.eliminarParticipantesGrupo(grupo1.getId(), usuario1.getId());

        assertEquals(1, grupo1.getUsuarios().size());
        assertFalse(grupo1.getUsuarios().contains(usuario1));

    }

    @Test
    @DisplayName("Test 16 --> Ver los grupos de un usuario")
    @Tag("Grupo")
    public void testListarGruposPositivo() {

        assertTrue(grupo1.getUsuarios().contains(usuario2));

        List<GrupoDTO> grupos = grupoService.listarGruposTC(usuario2.getId());

        assertSame(grupos.getFirst().getId(), grupo1.getId());

    }

    @Test
    @DisplayName("Test 17 --> Ver los grupos de un usuario que no exista")
    @Tag("Grupo")
    public void testListarGruposNegativo() {

        assertThrows(Exception.class, () -> grupoService.listarGrupos(675));

    }
}
