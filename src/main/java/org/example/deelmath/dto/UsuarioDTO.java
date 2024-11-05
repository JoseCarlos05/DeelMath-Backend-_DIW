package org.example.deelmath.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private String contrasena;
    private LocalDate fechaNacimiento;
    private Set<Integer> gastos;
    private Set<Integer> grupos;
    private Set<Integer> amigos;
    private Set<Integer> balance;
}
