package com.mx.orquestador.controller;

import com.mx.orquestador.model.UserCreate;
import com.mx.orquestador.model.Usuarioorq;
import com.mx.orquestador.service.OrquestadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/Orquestador")
@CrossOrigin("*")
public class OrquestadorController {
    @Autowired
    private OrquestadorService orquestadorService;
    @GetMapping("/usuarioPorEmail")
    public Mono<ResponseEntity<Usuarioorq>> getUsuarioPorEmail(@RequestParam String email) {
        return orquestadorService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/usuarioPorCurp")
    public Mono<ResponseEntity<Usuarioorq>> getUsuarioPorCurp(@RequestParam String curp) {
        return orquestadorService.buscarPorCurp(curp)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping("/crearUsuario")
    public Mono<ResponseEntity<Usuarioorq>> crearUsuario(@Valid @RequestBody UserCreate userCreate) {
        return orquestadorService.crearUsuario(userCreate)
                .map(usuario -> ResponseEntity.status(HttpStatus.CREATED).body(usuario))
                .onErrorResume(ResponseStatusException.class, e ->
                        Mono.just(new ResponseEntity<>(e.getStatusCode())));
    }
    @PostMapping("/actualizarUsuario")
    public Mono<ResponseEntity<Usuarioorq>> actualizarUsuario(@Valid @RequestBody Usuarioorq userUpdate) {
        return orquestadorService.actualizarUsuario(userUpdate)
                .map(usuarioActualizado -> ResponseEntity.ok(usuarioActualizado))
                .onErrorResume(ResponseStatusException.class, e ->
                        Mono.just(new ResponseEntity<>(e.getStatusCode())));
    }
}
