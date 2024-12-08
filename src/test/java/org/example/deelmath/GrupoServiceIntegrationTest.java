package org.example.deelmath;

import org.example.deelmath.dto.UsuarioDTO;
import org.example.deelmath.modelos.Grupo;
import org.example.deelmath.modelos.Moneda;
import org.example.deelmath.modelos.Usuario;
import org.example.deelmath.repository.IGrupoRepository;
import org.example.deelmath.service.GrupoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GrupoServiceIntegrationTest {

    @InjectMocks
    private GrupoService grupoService;

    @Mock
    private IGrupoRepository grupoRepository;

    @Test
    @DisplayName("Test 1 --> Ver participantes de un grupo")
    @Tag("Grupo")
    public void testVerParticipantesGrupoPositivo() {

        // GIVEN
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Antonio");
        usuario.setEmail("antonio123@gmail.com");
        usuario.setTelefono("601243565");
        usuario.setContrasena("123456");
        usuario.setFechaNacimiento(LocalDate.parse("2002-05-25"));

        Grupo grupo = new Grupo();
        grupo.setId(1);
        grupo.setNombre("Piso en Sevilla");
        grupo.setMoneda(Moneda.EUR);

        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(usuario);
        grupo.setUsuarios(usuarios);
        grupo.setCreador(usuario);

        when(grupoRepository.findById(1)).thenReturn(Optional.of(grupo));
        System.out.println("Mock de grupo configurado: " + grupo);

        // WHEN
        List<UsuarioDTO> participantes = grupoService.verParticipantesGrupo(1);

        // THEN
        assertNotNull(participantes);
        assertEquals(1, participantes.size());
        assertEquals(usuario.getNombre(), participantes.getFirst().getNombre());

        verify(grupoRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Test 2 --> Ver participantes de un grupo inexistente")
    @Tag("Grupo")
    public void testVerParticipantesGrupoNoExistente() {

        // GIVEN
        when(grupoRepository.findById(234)).thenReturn(Optional.empty());

        // WHEN && THEN
        assertThrows(RuntimeException.class, () -> grupoService.verParticipantesGrupo(234));

        verify(grupoRepository, times(1)).findById(234);
    }

}
