package org.example.deelmath;

import org.example.deelmath.dto.GastoDTO;
import org.example.deelmath.modelos.*;
import org.example.deelmath.repository.IBalanceRepository;
import org.example.deelmath.repository.IGrupoRepository;
import org.example.deelmath.repository.IUsuarioRepository;
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
public class GastoServiceTests {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private IGrupoRepository grupoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IBalanceRepository balanceRepository;

    Usuario usuario = new Usuario();
    Grupo grupo = new Grupo();

    Usuario usuario1 = new Usuario();

    @BeforeEach
    public void inicializarBaseDatos() {

        usuario.setNombre("Antonio");
        usuario.setEmail("antonio123@gmail.com");
        usuario.setTelefono("601243565");
        usuario.setContrasena("123456");
        usuario.setFechaNacimiento(LocalDate.parse("2002-05-25"));

        grupo.setNombre("Piso en Sevilla");
        grupo.setMoneda(Moneda.EUR);

        Set<Grupo> grupos = new HashSet<>();
        grupos.add(grupo);
        usuario.setGrupos(grupos);

        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(usuario);
        grupo.setUsuarios(usuarios);
        grupo.setCreador(usuario);

        usuarioRepository.save(usuario);
        grupoRepository.save(grupo);

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
    }


    @Test
    @DisplayName("Test 1 --> Crear un gasto en un grupo pagado por un usuario")
    @Tag("Gastos")
    public void testAnyadirGastoPositivo() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(123.45);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setId_usuario(usuario.getId());
        gasto.setId_grupo(grupo.getId());

        GastoDTO g = gastoService.anyadirGasto(gasto);

        assertNotNull(g);
        assertEquals(g.getTitulo(), gasto.getTitulo());
    }

    @Test
    @DisplayName("Test 2 --> Crear un gasto con una ID de grupo inexistente")
    @Tag("Gastos")
    public void testAnyadirGastoNegativoGrupo() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(123.45);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setId_usuario(usuario.getId());
        gasto.setId_grupo(5436);

        assertThrows(Exception.class, () -> gastoService.anyadirGasto(gasto));
    }

    @Test
    @DisplayName("Test 3 --> Crear un gasto con una ID de usuario inexistente")
    @Tag("Gastos")
    public void testAnyadirGastoNegativoUsuario() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(123.45);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setId_usuario(3463);
        gasto.setId_grupo(grupo.getId());

        assertThrows(Exception.class, () -> gastoService.anyadirGasto(gasto));
    }

    @Test
    @DisplayName("Test 4 --> Crear un gasto con una categoría inválida")
    @Tag("Gastos")
    public void testAnyadirGastoNegativoCategoria() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(123.45);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        try {
            gasto.setCategoria(Categoría.valueOf("carreras"));
        } catch (IllegalArgumentException e) {
            gasto.setCategoria(null);
        }
        gasto.setId_usuario(usuario.getId());
        gasto.setId_grupo(grupo.getId());

        assertThrows(IllegalArgumentException.class, () -> gastoService.anyadirGasto(gasto));
    }

    @Test
    @DisplayName("Test 5 --> Crear un gasto con un usuario que no esté en el grupo")
    @Tag("Gastos")
    public void testAnyadirGastoNegativoUsuarioFueraGrupo() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(123.45);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setId_usuario(usuario1.getId());
        gasto.setId_grupo(grupo.getId());

        assertThrows(IllegalArgumentException.class, () -> gastoService.anyadirGasto(gasto));
    }

    @Test
    @DisplayName("Test 6 --> Crear un gasto sin usuario")
    @Tag("Gastos")
    public void testAnyadirGastoNegativoSinUsuario() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(123.45);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setId_usuario(null);
        gasto.setId_grupo(grupo.getId());

        assertThrows(Exception.class, () -> gastoService.anyadirGasto(gasto));
    }

    @Test
    @DisplayName("Test 7 --> Crear un gasto sin grupo")
    @Tag("Gastos")
    public void testAnyadirGastoNegativoSinGrupo() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(123.45);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setId_usuario(usuario.getId());
        gasto.setId_grupo(null);

        assertThrows(Exception.class, () -> gastoService.anyadirGasto(gasto));
    }

    @Test
    @DisplayName("Test 8 --> Crear un gasto con un coste negativo")
    @Tag("Gastos")
    public void testAnyadirGastoNegativoCoste() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(-123.45);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setId_usuario(usuario.getId());
        gasto.setId_grupo(grupo.getId());

        assertThrows(IllegalArgumentException.class, () -> gastoService.anyadirGasto(gasto));
    }

    @Test
    @DisplayName("Test 9 --> Crear un gasto con coste cero")
    @Tag("Gastos")
    public void testAnyadirGastoNegativoCosteCero() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(0);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setId_usuario(usuario.getId());
        gasto.setId_grupo(grupo.getId());

        assertThrows(IllegalArgumentException.class, () -> gastoService.anyadirGasto(gasto));
    }

    @Test
    @DisplayName("Test 10 --> Verificar cálculo del balance tras crear un gasto")
    @Tag("Gastos")
    public void testAnyadirGastoBalancePositivo() {

        GastoDTO gasto = new GastoDTO();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(300);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setId_usuario(usuario.getId());
        gasto.setId_grupo(grupo.getId());

        gastoService.anyadirGasto(gasto);

        Balance balanceUsuario = balanceRepository.findByUsuarioAndGrupo(usuario, grupo);
        assertNotNull(balanceUsuario);
        assertEquals(300.0 - (300.0 / grupo.getUsuarios().size()), balanceUsuario.getBalance());

        for (Usuario u : grupo.getUsuarios()) {
            if (!u.getId().equals(usuario.getId())) {
                Balance balanceOtros = balanceRepository.findByUsuarioAndGrupo(u, grupo);
                assertNotNull(balanceOtros);
                assertEquals(-(300.0 / grupo.getUsuarios().size()), balanceOtros.getBalance());
            }
        }
    }

    @Test
    @DisplayName("Test 11 --> Ver los gastos de un grupo")
    @Tag("Gastos")
    public void testVerGastosPositivo() {

        Gasto gasto = new Gasto();
        gasto.setTitulo("Recibo de la luz");
        gasto.setCoste(200);
        gasto.setFecha(LocalDate.parse("2024-12-08"));
        gasto.setCategoria(Categoría.LUZ);
        gasto.setUsuario(usuario);
        gasto.setGrupo(grupo);

        Set<Gasto> listaGastos = new HashSet<>();
        listaGastos.add(gasto);
        grupo.setGastos(listaGastos);

        List<GastoDTO> gastos = gastoService.verGastos(grupo.getId());

        assertNotNull(gastos);
        assertEquals(1, gastos.size());
        assertEquals(gastos.getFirst().getTitulo(), gasto.getTitulo());
        assertEquals(gastos.getFirst().getId_usuario(), usuario.getId());
    }

    @Test
    @DisplayName("Test 12 --> Ver los gastos de un grupo inexistente")
    @Tag("Gastos")
    public void testVerGastosNegativo() {

        assertThrows(Exception.class, () -> gastoService.verGastos(456));

    }
}
