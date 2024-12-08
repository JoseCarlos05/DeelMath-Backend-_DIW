package org.example.deelmath;

import org.example.deelmath.dto.GastoDTO;
import org.example.deelmath.modelos.*;
import org.example.deelmath.repository.IGastoRepository;
import org.example.deelmath.repository.IGrupoRepository;
import org.example.deelmath.repository.IUsuarioRepository;
import org.example.deelmath.service.GastoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GastoServiceIntegrationTest {

    @Mock
    private IGastoRepository gastoRepository;

    @Mock
    private IGrupoRepository grupoRepository;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private GastoService gastoService;

    @Test
    @DisplayName("Test --> Crear un gasto con un usuario que no esté en el grupo")
    @Tag("Gastos")
    public void testAnyadirGastoNegativoUsuarioFueraGrupo() {

        //Given
        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        usuario1.setNombre("Paco");
        usuario1.setEmail("paco123@gmail.com");
        usuario1.setTelefono("601929384");
        usuario1.setContrasena("123456");
        usuario1.setFechaNacimiento(LocalDate.parse("2002-04-12"));

        Grupo grupo = new Grupo();
        grupo.setId(1);
        grupo.setNombre("Piso en Sevilla");
        grupo.setMoneda(Moneda.EUR);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario1));
        when(grupoRepository.findById(1)).thenReturn(Optional.of(grupo));

        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setTitulo("Recibo de la luz");
        gastoDTO.setCoste(123.45);
        gastoDTO.setFecha(LocalDate.parse("2024-12-08"));
        gastoDTO.setCategoria(Categoría.LUZ);
        gastoDTO.setId_usuario(usuario1.getId());
        gastoDTO.setId_grupo(grupo.getId());

        //WHEN && THEN
        assertThrows(IllegalArgumentException.class, () -> gastoService.anyadirGasto(gastoDTO));

        verify(gastoRepository, times(0)).save(any(Gasto.class));
    }
}


