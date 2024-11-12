package org.example.deelmath.controller;

import org.example.deelmath.dto.UsuarioInicioDTO;
import org.example.deelmath.service.GrupoService;
import org.example.deelmath.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/{email}/{contrasena}")
    public UsuarioInicioDTO buscarUsuario(@PathVariable String email, @PathVariable String contrasena) {
        return usuarioService.buscarUsuario(email, contrasena);
    }
}
