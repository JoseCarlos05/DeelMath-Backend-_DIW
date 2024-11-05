package org.example.deelmath.service;

import org.example.deelmath.dto.GastoDTO;
import org.example.deelmath.dto.GrupoDTO;
import org.example.deelmath.modelos.Balance;
import org.example.deelmath.modelos.Gasto;
import org.example.deelmath.modelos.Grupo;
import org.example.deelmath.modelos.Usuario;
import org.example.deelmath.repository.IBalanceRepository;
import org.example.deelmath.repository.IGastoRepository;
import org.example.deelmath.repository.IGrupoRepository;
import org.example.deelmath.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GastoService {

    private final IGastoRepository gastoRepository;
    private final IGrupoRepository grupoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IBalanceRepository balanceRepository;

    public GastoService(IGastoRepository gastoRepository, IGrupoRepository grupoRepository, IUsuarioRepository usuarioRepository, IBalanceRepository balanceRepository) {
        this.gastoRepository = gastoRepository;
        this.grupoRepository = grupoRepository;
        this.usuarioRepository = usuarioRepository;
        this.balanceRepository = balanceRepository;
    }

    public GastoDTO anyadirGasto(GastoDTO gastoDTO) {

        Gasto gasto = new Gasto();

        gasto.setTitulo(gastoDTO.getTitulo());
        gasto.setCoste(gastoDTO.getCoste());
        gasto.setFecha(gastoDTO.getFecha());
        gasto.setCategoria(gastoDTO.getCategoria());

        if (gastoDTO.getId_grupo() != null) {
            gasto.setGrupo(grupoRepository.findById(gastoDTO.getId_grupo()).get());
        }

        if (gastoDTO.getId_usuario() != null) {
            gasto.setUsuario(usuarioRepository.findById(gastoDTO.getId_usuario()).get());
        }

        Gasto g = gastoRepository.save(gasto);

        Usuario usuario = usuarioRepository.findById(gastoDTO.getId_usuario()).get();
        Grupo grupo = grupoRepository.findById(gastoDTO.getId_grupo()).get();
        Balance balance = balanceRepository.findByUsuarioAndGrupo(usuario, grupo);

        double deuda = gastoDTO.getCoste()/grupo.getUsuarios().size();
        
        if (balance == null) {

            for (Usuario u : grupo.getUsuarios()) {
                balance = new Balance();
                balance.setUsuario(u);
                balance.setGrupo(grupo);
                if (u.getId().equals(usuario.getId())) {
                    balance.setBalance(gasto.getCoste());
                } else {
                    balance.setBalance(-deuda);
                }
                balanceRepository.save(balance);
            }

        } else {

            for (Usuario u : grupo.getUsuarios()) {
                balance = balanceRepository.findByUsuarioAndGrupo(u, grupo);
                if (u.getId().equals(usuario.getId())) {
                    balance.setBalance(balance.getBalance() + gasto.getCoste());
                } else {
                    balance.setBalance(balance.getBalance() - deuda);
                }
                balanceRepository.save(balance);
            }

        }


        return getGastoDTO(g);
    }

    public List<GastoDTO> verGastos(Integer idGrupo) {

        if (!grupoRepository.existsById(idGrupo)) {
            throw new RuntimeException("No existe un grupo con este ID.");
        }

        Grupo grupo = grupoRepository.findById(idGrupo).get();

        List<GastoDTO> gastoDTOs = new ArrayList<>();
        for (Gasto gasto : grupo.getGastos()) {
            GastoDTO gastoDTO = getGastoDTO(gasto);
            gastoDTOs.add(gastoDTO);
        }

        return gastoDTOs;
    }

    private static GastoDTO getGastoDTO(Gasto g) {
        GastoDTO dtonuevo = new GastoDTO();

        dtonuevo.setTitulo(g.getTitulo());
        dtonuevo.setCoste(g.getCoste());
        dtonuevo.setFecha(g.getFecha());
        dtonuevo.setCategoria(g.getCategoria());

        if (g.getUsuario() != null) {
            dtonuevo.setId_usuario(g.getUsuario().getId());
        }

        if (g.getGrupo() != null) {
            dtonuevo.setId_grupo(g.getGrupo().getId());
        }

        return dtonuevo;
    }
}
