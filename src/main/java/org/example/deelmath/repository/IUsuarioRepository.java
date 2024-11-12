package org.example.deelmath.repository;

import org.example.deelmath.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByEmailAndContrasena(String email, String contrasena);
}
