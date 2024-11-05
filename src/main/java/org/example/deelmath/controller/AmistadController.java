package org.example.deelmath.controller;

import org.example.deelmath.dto.UsuarioDTO;
import org.example.deelmath.service.AmistadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class AmistadController {

    @Autowired
    private AmistadService amistadService;

    @GetMapping("/amigos/{idUsuario}")
    public List<UsuarioDTO> listarAmigos(@PathVariable Integer idUsuario){
        return amistadService.listarAmigos(idUsuario);
    }
}
