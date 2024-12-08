package org.example.deelmath;

import org.example.deelmath.dto.BalanceDTO;
import org.example.deelmath.modelos.*;
import org.example.deelmath.repository.IBalanceRepository;
import org.example.deelmath.repository.IGastoRepository;
import org.example.deelmath.repository.IGrupoRepository;
import org.example.deelmath.repository.IUsuarioRepository;
import org.example.deelmath.service.BalanceService;
import org.example.deelmath.service.GastoService;
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
public class BalanceServiceTests {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private IGrupoRepository grupoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IBalanceRepository balanceRepository;

    @Autowired
    private IGastoRepository gastoRepository;

    @Autowired
    private BalanceService balanceService;

    Usuario usuario = new Usuario();
    Usuario usuario1 = new Usuario();
    Grupo grupo = new Grupo();

    @BeforeEach
    public void inicializarBaseDatos() {

        usuario.setNombre("Antonio");
        usuario.setEmail("antonio123@gmail.com");
        usuario.setTelefono("601243565");
        usuario.setContrasena("123456");
        usuario.setFechaNacimiento(LocalDate.parse("2002-05-25"));

        usuario1.setNombre("Paco");
        usuario1.setEmail("paco123@gmail.com");
        usuario1.setTelefono("601929384");
        usuario1.setContrasena("123456");
        usuario1.setFechaNacimiento(LocalDate.parse("2002-04-12"));

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

        usuarioRepository.save(usuario);
        grupoRepository.save(grupo);

        Balance balanceU = new Balance();
        balanceU.setBalance(100.0);
        balanceU.setUsuario(usuario);
        balanceU.setGrupo(grupo);
        balanceRepository.save(balanceU);

        Balance balanceU1 = new Balance();
        balanceU1.setBalance(-100.0);
        balanceU1.setUsuario(usuario1);
        balanceU1.setGrupo(grupo);
        balanceRepository.save(balanceU1);

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
    }


    @Test
    @DisplayName("Test 1 --> Ver los balances de un grupo")
    @Tag("Balances")
    public void testVerBalancesPositivo() {

        List<BalanceDTO> balances = balanceService.verBalances(grupo.getId());

        assertNotNull(balances);
        assertEquals(2, balances.size());

    }

    @Test
    @DisplayName("Test 2 --> Ver los balances de un grupo inexistente")
    @Tag("Balances")
    public void testVerBalancesNegativo() {

        assertThrows(Exception.class, () -> balanceService.verBalances(4574));

    }

}
