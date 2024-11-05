package org.example.deelmath.controller;

import org.example.deelmath.dto.GastoDTO;
import org.example.deelmath.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @PostMapping("/grupo/gasto/nuevo")
    public GastoDTO anyadirGasto(@RequestBody GastoDTO gastoDTO) {
        return gastoService.anyadirGasto(gastoDTO);
    }

    @GetMapping("/grupo/gastos/{idGrupo}")
    public List<GastoDTO> verGastos(@PathVariable Integer idGrupo) {
        return gastoService.verGastos(idGrupo);
    }

}
