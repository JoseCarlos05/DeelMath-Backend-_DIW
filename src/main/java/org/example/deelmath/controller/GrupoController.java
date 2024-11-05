package org.example.deelmath.controller;

import org.example.deelmath.dto.GrupoDTO;
import org.example.deelmath.dto.UsuarioDTO;
import org.example.deelmath.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @PostMapping("/grupo/nuevo")
    public GrupoDTO crearGrupo(@RequestBody GrupoDTO grupoDTO) {
        return grupoService.crearGrupo(grupoDTO);
    }

    @PutMapping("/grupo/{idGrupo}/participantes/{idUsuario}/nuevo")
    public GrupoDTO anyadirParticipantesGrupo(@PathVariable Integer idGrupo, @PathVariable Integer idUsuario) {
        return grupoService.anyadirParticipantesGrupo(idGrupo, idUsuario);
    }

    @GetMapping("/grupo/{idGrupo}/participantes")
    public List<UsuarioDTO> verParticipantesGrupo(@PathVariable Integer idGrupo){
        return grupoService.verParticipantesGrupo(idGrupo);
    }

    @PutMapping("/grupo/{idGrupo}/participantes/{idUsuario}/eliminar")
    public GrupoDTO eliminarParticipantesGrupo(@PathVariable Integer idGrupo, @PathVariable Integer idUsuario) {
        return grupoService.eliminarParticipantesGrupo(idGrupo, idUsuario);
    }

    @GetMapping("/grupo/{idUsuario}")
    public List<GrupoDTO> listarGrupos(@PathVariable Integer idUsuario){
        return grupoService.listarGrupos(idUsuario);
    }
}
