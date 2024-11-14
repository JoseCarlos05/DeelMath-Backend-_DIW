package org.example.deelmath.controller;

import org.example.deelmath.modelos.Moneda;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("")
public class MonedaController {

    @GetMapping("/monedas")
    public List<Moneda> listaMonedas() {
        return Arrays.asList(Moneda.values());
    }
}
