package org.example.deelmath.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.deelmath.modelos.Gasto;
import org.example.deelmath.modelos.Moneda;
import org.example.deelmath.modelos.Usuario;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoNombreDTO {
    private String nombre;
}