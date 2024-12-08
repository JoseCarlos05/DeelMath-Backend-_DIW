package org.example.deelmath.service;

import org.example.deelmath.dto.GrupoDTO;
import org.example.deelmath.dto.GrupoNombreDTO;
import org.example.deelmath.dto.UsuarioDTO;
import org.example.deelmath.modelos.*;
import org.example.deelmath.repository.IBalanceRepository;
import org.example.deelmath.repository.IGastoRepository;
import org.example.deelmath.repository.IGrupoRepository;
import org.example.deelmath.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GrupoService {

    private final IGrupoRepository grupoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IGastoRepository gastoRepository;
    private final IBalanceRepository balanceRepository;
    private final UsuarioService usuarioService;

    public GrupoService(IGrupoRepository grupoRepository, IUsuarioRepository usuarioRepository, IGastoRepository gastoRepository, IBalanceRepository balanceRepository, UsuarioService usuarioService) {
        this.grupoRepository = grupoRepository;
        this.usuarioRepository = usuarioRepository;
        this.gastoRepository = gastoRepository;
        this.balanceRepository = balanceRepository;
        this.usuarioService = usuarioService;
    }

    public GrupoDTO crearGrupo(GrupoDTO grupoDTO) throws Exception {

        Grupo grupo = null;

        if (grupoDTO.getId() != null) {
            grupo = grupoRepository.findById(grupoDTO.getId()).get();
        }

        if (grupo == null) {
            grupo = new Grupo();
        }

        grupo.setNombre(grupoDTO.getNombre());
        if (grupoDTO.getMoneda() == null || !EnumSet.allOf(Moneda.class).contains(grupoDTO.getMoneda())) {
            throw new IllegalArgumentException("Moneda no válida");
        }
        grupo.setMoneda(grupoDTO.getMoneda());

        if (grupoDTO.getUsuarios() != null) {
            Set<Usuario> usuarios = new HashSet<>();
            for (Integer u : grupoDTO.getUsuarios()) {
                usuarios.add(usuarioRepository.findById(u).get());
            }
            grupo.setUsuarios(usuarios);
        }

        if (grupoDTO.getGastos() != null) {
            Set<Gasto> gastos = new HashSet<>();
            for (Integer ga : grupoDTO.getGastos()) {
                gastos.add(gastoRepository.findById(ga).get());

            }
            grupo.setGastos(gastos);
        }

        if (grupoDTO.getBalances() != null) {
            Set<Balance> balances = new HashSet<>();
            for (Integer b : grupoDTO.getGastos()) {
                balances.add(balanceRepository.findById(b).get());

            }
            grupo.setBalances(balances);
        }

        if (grupoDTO.getCreador() != null) {
            Usuario usuario = usuarioRepository.findById(grupoDTO.getCreador()).get();
            grupo.setCreador(usuario);
            if (!grupo.getUsuarios().contains(grupo.getCreador())) {
                throw new Exception("El usuario creador no está en el grupo");
            }
        }

        Grupo g = grupoRepository.save(grupo);
        return getGrupoDTO(g);

    }


    public GrupoDTO anyadirParticipantesGrupo(Integer idGrupo, Integer idUsuario) {

        if (!grupoRepository.existsById(idGrupo)) {
            throw new RuntimeException("No existe un grupo con este ID.");
        }
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("No existe un usuario con este ID.");
        }

        Grupo grupo = grupoRepository.findById(idGrupo).get();
        Usuario usuario = usuarioRepository.findById(idUsuario).get();

        if (!grupo.getUsuarios().contains(usuario)) {

            grupo.getUsuarios().add(usuario);
            usuario.getGrupos().add(grupo);

            grupoRepository.save(grupo);
            usuarioRepository.save(usuario);

        }

        return getGrupoDTO(grupo);
    }


    public List<UsuarioDTO> verParticipantesGrupo(Integer idGrupo) {

        if (!grupoRepository.existsById(idGrupo)) {
            throw new RuntimeException("No existe un grupo con este ID.");
        }

        Grupo grupo = grupoRepository.findById(idGrupo).get();

        List<UsuarioDTO> listaUDTOs = new ArrayList<>();
        for (Usuario u : grupo.getUsuarios()) {
            UsuarioDTO uDTO = usuarioService.getUsuarioDTO(u);
            listaUDTOs.add(uDTO);
        }

        return listaUDTOs;
    }


    public GrupoDTO eliminarParticipantesGrupo(Integer idGrupo, Integer idUsuario) {

        if (!grupoRepository.existsById(idGrupo)) {
            throw new RuntimeException("No existe un grupo con este ID.");
        }
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("No existe un usuario con este ID.");
        }

        Grupo grupo = grupoRepository.findById(idGrupo).get();
        Usuario usuario = usuarioRepository.findById(idUsuario).get();

        if (grupo.getUsuarios().contains(usuario)) {

            grupo.getUsuarios().remove(usuario);
            usuario.getGrupos().remove(grupo);

            grupoRepository.save(grupo);
            usuarioRepository.save(usuario);

        }

        return getGrupoDTO(grupo);
    }

    public List<GrupoDTO> listarGruposTC(Integer idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("No existe un usuario con este ID.");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario).get();

        List<GrupoDTO> listaGDTOs = new ArrayList<>();
        for (Grupo g : usuario.getGrupos()) {
            GrupoDTO gDTO = getGrupoDTO(g);
            listaGDTOs.add(gDTO);
        }

        return listaGDTOs;
    }

    public List<GrupoNombreDTO> listarGrupos(Integer idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("No existe un usuario con este ID.");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario).get();

        List<GrupoNombreDTO> listaGDTOs = new ArrayList<>();
        for (Grupo g : usuario.getGrupos()) {
            GrupoNombreDTO gDTO = getGrupoNombreDTO(g);
            listaGDTOs.add(gDTO);
        }

        return listaGDTOs;
    }


    private static GrupoDTO getGrupoDTO(Grupo g) {
        GrupoDTO dtonuevo = new GrupoDTO();

        dtonuevo.setId(g.getId());
        dtonuevo.setNombre(g.getNombre());
        dtonuevo.setMoneda(g.getMoneda());

        if (g.getUsuarios() != null) {
            Set<Integer> usuariosDTO = new HashSet<>();
            for (Usuario u : g.getUsuarios()) {
                usuariosDTO.add(u.getId());
            }
            dtonuevo.setUsuarios(usuariosDTO);
        }

        if (g.getGastos() != null) {
            Set<Integer> gastosDTO = new HashSet<>();
            for (Gasto ga : g.getGastos()) {
                gastosDTO.add(ga.getId());
            }
            dtonuevo.setGastos(gastosDTO);
        }

        if (g.getBalances() != null) {
            Set<Integer> balancesDTO = new HashSet<>();
            for (Balance b : g.getBalances()) {
                balancesDTO.add(b.getId());
            }
            dtonuevo.setBalances(balancesDTO);
        }

        if (g.getCreador() != null) {
            dtonuevo.setCreador(g.getCreador().getId());
        }

        return dtonuevo;
    }

    private static GrupoNombreDTO getGrupoNombreDTO(Grupo g) {
        GrupoNombreDTO dtonuevo = new GrupoNombreDTO();

        dtonuevo.setId(g.getId());
        dtonuevo.setNombre(g.getNombre());

        if (g.getCreador() != null) {
            dtonuevo.setCreador(g.getCreador().getId());
        }

        return dtonuevo;
    }
}
