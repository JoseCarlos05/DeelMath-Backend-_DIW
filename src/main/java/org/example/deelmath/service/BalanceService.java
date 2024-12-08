package org.example.deelmath.service;

import org.example.deelmath.dto.BalanceDTO;
import org.example.deelmath.dto.GastoDTO;
import org.example.deelmath.modelos.Balance;
import org.example.deelmath.modelos.Gasto;
import org.example.deelmath.modelos.Grupo;
import org.example.deelmath.repository.IBalanceRepository;
import org.example.deelmath.repository.IGastoRepository;
import org.example.deelmath.repository.IGrupoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceService {

    private final IBalanceRepository balanceRepository;
    private final IGrupoRepository grupoRepository;

    public BalanceService(IBalanceRepository balanceRepository, IGrupoRepository grupoRepository) {
        this.balanceRepository = balanceRepository;
        this.grupoRepository = grupoRepository;
    }

    public List<BalanceDTO> verBalances(Integer idGrupo) {

        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("No existe un grupo con este ID."));

        List<BalanceDTO> balancesDTOs = new ArrayList<>();
        for (Balance balance : grupo.getBalances()) {
            BalanceDTO balanceDTO = getBalanceDTO(balance);
            balancesDTOs.add(balanceDTO);
        }

        return balancesDTOs;
    }

    private static BalanceDTO getBalanceDTO(Balance b) {
        BalanceDTO dtonuevo = new BalanceDTO();

        dtonuevo.setBalance(b.getBalance());

        if (b.getUsuario() != null) {
            dtonuevo.setIdUsuario(b.getUsuario().getId());
        }

        return dtonuevo;
    }
}
