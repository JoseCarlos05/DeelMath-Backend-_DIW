package org.example.deelmath.repository;

import org.example.deelmath.modelos.Balance;
import org.example.deelmath.modelos.Grupo;
import org.example.deelmath.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBalanceRepository extends JpaRepository<Balance, Integer> {

    public Balance findByUsuarioAndGrupo(Usuario usuario, Grupo grupo);
}
