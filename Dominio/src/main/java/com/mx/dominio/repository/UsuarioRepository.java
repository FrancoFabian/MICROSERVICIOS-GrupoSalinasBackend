package com.mx.dominio.repository;

import com.mx.dominio.entity.Usuarioorq;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UsuarioRepository extends R2dbcRepository<Usuarioorq,Long> {
    Mono<Usuarioorq> findByCurp(String curp);

    // Buscar por email
    Mono<Usuarioorq> findByEmail(String email);
}
