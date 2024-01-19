package com.mx.dominio.service;

import com.mx.dominio.entity.Usuarioorq;
import com.mx.dominio.repository.UsuarioRepository;
import com.mx.dominio.request.UserCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class UsuarioImpl implements UsuarioService{
    @Autowired
    UsuarioRepository uRepository;
    @Override
    public Mono<Usuarioorq> createUser(UserCreate userCreate) {
        Usuarioorq usuario = new Usuarioorq();
        usuario.setNombre(userCreate.getNombre());
        usuario.setEmail(userCreate.getEmail());
        usuario.setTelefono(userCreate.getTelefono());
        usuario.setCurp(userCreate.getCurp());
        usuario.setCodigoPostal(userCreate.getCodigoPostal());
        return uRepository.save(usuario);
    }

    @Override
    public Flux<Usuarioorq> listarUsers() {
        return uRepository.findAll();
    }

    @Override
    public Mono<Usuarioorq> updateUser(Usuarioorq usuarioorq) {

        return uRepository.save(usuarioorq);
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return uRepository.findById(id)
                .flatMap(user -> uRepository.deleteById(user.getId()))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")))
                .onErrorResume(e -> {
                    // Aquí puedes manejar diferentes tipos de excepciones si es necesario
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar usuario"));
                });
    }

    @Override
    public Mono<Usuarioorq> searchEmail(String email) {
        return uRepository.findByEmail(email)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Usuarioorq> searchCurp(String curp) {
        return uRepository.findByCurp(curp)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Usuarioorq> searchId(Long id) {
        return uRepository.findById(id)
                .switchIfEmpty(Mono.empty());
    }
}
