package com.mx.dominio.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("usuarioorq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuarioorq {
    @Id
    private Long id;

    @Column("nombre")
    private String nombre;

    @Column("email")
    private String email;

    @Column("telefono")
    private String telefono;

    @Column("curp")
    private String curp;

    @Column("codigo_postal")
    private String codigoPostal;

}
