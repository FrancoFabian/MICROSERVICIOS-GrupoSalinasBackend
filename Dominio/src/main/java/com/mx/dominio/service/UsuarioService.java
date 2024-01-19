package com.mx.dominio.service;

import com.mx.dominio.entity.Usuarioorq;
import com.mx.dominio.request.UserCreate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioService {
    Mono<Usuarioorq>createUser(UserCreate userCreate);
    Flux<Usuarioorq>listarUsers();
    Mono<Usuarioorq>updateUser(Usuarioorq usuarioorq);
    Mono<Void>deleteUser(Long id);
    Mono<Usuarioorq>searchEmail(String email);
    Mono<Usuarioorq>searchCurp(String curp);
    Mono<Usuarioorq>searchId(Long id);

}
