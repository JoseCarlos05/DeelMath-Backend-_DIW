package org.example.deelmath.controller;

import org.example.deelmath.dto.BalanceDTO;
import org.example.deelmath.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/grupo/{idGrupo}/balances")
    public List<BalanceDTO> verBalances(@PathVariable Integer idGrupo) {
        return balanceService.verBalances(idGrupo);
    }
}
