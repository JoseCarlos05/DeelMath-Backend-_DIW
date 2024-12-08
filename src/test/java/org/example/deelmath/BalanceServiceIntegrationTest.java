package org.example.deelmath;

import org.example.deelmath.dto.BalanceDTO;
import org.example.deelmath.modelos.Balance;
import org.example.deelmath.modelos.Grupo;
import org.example.deelmath.modelos.Moneda;
import org.example.deelmath.modelos.Usuario;
import org.example.deelmath.repository.IBalanceRepository;
import org.example.deelmath.repository.IGrupoRepository;
import org.example.deelmath.repository.IUsuarioRepository;
import org.example.deelmath.service.BalanceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class BalanceServiceIntegrationTest {

    @Mock
    private IGrupoRepository grupoRepository;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IBalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    @DisplayName("Test --> Ver los balances de un grupo")
    @Tag("Balances")
    public void testVerBalancesPositivo() {

        //GIVEN

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Antonio");
        usuario.setEmail("antonio123@gmail.com");
        usuario.setTelefono("601243565");
        usuario.setContrasena("123456");
        usuario.setFechaNacimiento(LocalDate.parse("2002-05-25"));

        Usuario usuario1 = new Usuario();
        usuario.setId(2);
        usuario1.setNombre("Paco");
        usuario1.setEmail("paco123@gmail.com");
        usuario1.setTelefono("601929384");
        usuario1.setContrasena("123456");
        usuario1.setFechaNacimiento(LocalDate.parse("2002-04-12"));

        Grupo grupo = new Grupo();
        grupo.setId(1);
        grupo.setNombre("Piso en Sevilla");
        grupo.setMoneda(Moneda.EUR);

        Set<Grupo> grupos = new HashSet<>();
        grupos.add(grupo);
        usuario.setGrupos(grupos);
        usuario1.setGrupos(grupos);

        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(usuario);
        usuarios.add(usuario1);
        grupo.setUsuarios(usuarios);
        grupo.setCreador(usuario);

        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioRepository.save(usuario1)).thenReturn(usuario1);
        when(grupoRepository.save(grupo)).thenReturn(grupo);

        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario1);
        grupoRepository.save(grupo);

        // Crear los balances
        Balance balanceU = new Balance();
        balanceU.setBalance(100.0);
        balanceU.setUsuario(usuario);
        balanceU.setGrupo(grupo);
        when(balanceRepository.save(balanceU)).thenReturn(balanceU);

        Balance balanceU1 = new Balance();
        balanceU1.setBalance(-100.0);
        balanceU1.setUsuario(usuario1);
        balanceU1.setGrupo(grupo);
        when(balanceRepository.save(balanceU1)).thenReturn(balanceU1);

        Set<Balance> balances = new HashSet<>();
        balances.add(balanceU);
        balances.add(balanceU1);
        grupo.setBalances(balances);

        Set<Balance> balancesU = new HashSet<>();
        balances.add(balanceU);
        usuario.setBalances(balancesU);

        Set<Balance> balancesU1 = new HashSet<>();
        balances.add(balanceU1);
        usuario1.setBalances(balancesU1);

        when(grupoRepository.findById(1)).thenReturn(Optional.of(grupo));
        when(balanceRepository.findAll()).thenReturn(List.of(balanceU, balanceU1));

        //WHEN
        List<BalanceDTO> balancesDTO = balanceService.verBalances(grupo.getId());

        //THEN
        assertNotNull(balancesDTO);
        assertEquals(2, balancesDTO.size());
    }
}
