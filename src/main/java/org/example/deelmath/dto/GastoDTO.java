package org.example.deelmath.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.deelmath.modelos.Categoría;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoDTO {
    private String titulo;
    private double coste;
    private LocalDate fecha;
    private Categoría categoria;
    private Integer id_usuario;
    private Integer id_grupo;
}
