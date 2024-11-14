package org.example.deelmath.service;

import org.example.deelmath.dto.UsuarioDTO;
import org.example.deelmath.dto.UsuarioInicioDTO;
import org.example.deelmath.modelos.Amistad;
import org.example.deelmath.modelos.Usuario;
import org.example.deelmath.repository.IAmistadRepository;
import org.example.deelmath.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AmistadService {

    private final IAmistadRepository amistadRepository;
    private final IUsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public AmistadService(IAmistadRepository amistadRepository, IUsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.amistadRepository = amistadRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    public List<UsuarioInicioDTO> listarAmigos(Integer idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RuntimeException("No existe un usuario con este ID.");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario).get();

        List<UsuarioInicioDTO> usuarioDTOs = new ArrayList<>();
        for (Amistad a : usuario.getAmigos()) {
            Usuario u = usuarioRepository.findById(a.getAmigo().getId()).get();
            UsuarioInicioDTO dto = usuarioService.getUsuarioInicioDTO(u);
            usuarioDTOs.add(dto);
        }

        return usuarioDTOs;
    }
}
