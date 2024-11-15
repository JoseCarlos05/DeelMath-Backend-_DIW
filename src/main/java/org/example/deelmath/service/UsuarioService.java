package org.example.deelmath.service;

import org.example.deelmath.dto.UsuarioDTO;
import org.example.deelmath.dto.UsuarioInicioDTO;
import org.example.deelmath.modelos.*;
import org.example.deelmath.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioInicioDTO buscarUsuarioID(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        return getUsuarioInicioDTO(usuario);
    }

    public UsuarioInicioDTO buscarUsuarioEC(String email,String contrasena) {
        Usuario usuario = usuarioRepository.findByEmailAndContrasena(email, contrasena);
        return getUsuarioInicioDTO(usuario);
    }

    public static UsuarioDTO getUsuarioDTO(Usuario u) {
        UsuarioDTO dtonuevo = new UsuarioDTO();

        dtonuevo.setId(u.getId());
        dtonuevo.setNombre(u.getNombre());
        dtonuevo.setEmail(u.getEmail());
        dtonuevo.setTelefono(u.getTelefono());
        dtonuevo.setContrasena(u.getContrasena());
        dtonuevo.setFechaNacimiento(u.getFechaNacimiento());

        if (u.getGastos() != null) {
            Set<Integer> gastosDTO = new HashSet<>();
            for (Gasto g : u.getGastos()) {
                gastosDTO.add(g.getId());
            }
            dtonuevo.setGastos(gastosDTO);
        }

        if (u.getGrupos() != null) {
            Set<Integer> gruposDTO = new HashSet<>();
            for (Grupo g : u.getGrupos()) {
                gruposDTO.add(g.getId());
            }
            dtonuevo.setGrupos(gruposDTO);
        }

        if (u.getAmigos() != null) {
            Set<Integer> amigosDTO = new HashSet<>();
            for (Amistad a : u.getAmigos()) {
                amigosDTO.add(a.getId());
            }
            dtonuevo.setAmigos(amigosDTO);
        }

        if (u.getBalances() != null) {
            Set<Integer> balancesDTO = new HashSet<>();
            for (Balance b : u.getBalances()) {
                balancesDTO.add(b.getId());
            }
            dtonuevo.setBalance(balancesDTO);
        }

        return dtonuevo;
    }

    public static UsuarioInicioDTO getUsuarioInicioDTO(Usuario u) {
        UsuarioInicioDTO dtonuevo = new UsuarioInicioDTO();

        dtonuevo.setId(u.getId());
        dtonuevo.setNombre(u.getNombre());
        dtonuevo.setEmail(u.getEmail());
        dtonuevo.setContrasena(u.getContrasena());

        return dtonuevo;
    }

}
