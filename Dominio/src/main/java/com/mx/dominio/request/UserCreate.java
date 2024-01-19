package com.mx.dominio.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreate {
    private String nombre;
    private String email;
    private String telefono;
    private String curp;
    private String codigoPostal;
}
