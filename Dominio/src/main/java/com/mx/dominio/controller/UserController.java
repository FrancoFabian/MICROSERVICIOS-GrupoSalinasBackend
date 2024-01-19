package com.mx.dominio.controller;

import com.mx.dominio.entity.Usuarioorq;
import com.mx.dominio.request.UserCreate;
import com.mx.dominio.service.UsuarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/Usuarios")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UsuarioImpl usuarioService;
    @GetMapping("hola")
    public Flux<Usuarioorq> hola(){
        Flux<Usuarioorq> lalista = usuarioService.listarUsers();
        return lalista;
    }
    @GetMapping("/buscarPorEmail")
    public Mono<ResponseEntity<Usuarioorq>> buscarPorEmail(@RequestParam String email) {
        return usuarioService.searchEmail(email)
                .map(usuario -> ResponseEntity.ok(usuario))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorCurp")
    public Mono<ResponseEntity<Usuarioorq>> buscarPorCurp(@RequestParam String curp) {
        return usuarioService.searchCurp(curp)
                .map(usuario -> ResponseEntity.ok(usuario))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/buscarPorId")
    public Mono<ResponseEntity<Usuarioorq>> buscarPorId(@RequestParam Long id) {
        return usuarioService.searchId(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping("/crear")
    public Mono<Usuarioorq> crearUsuario(@RequestBody UserCreate userCreate) {
        return usuarioService.createUser(userCreate);
    }
    @PostMapping("/update")
    public Mono<Usuarioorq> updateUsuario(@RequestBody Usuarioorq userUpdate) {
        return usuarioService.updateUser(userUpdate);
    }
    @DeleteMapping("/deleteUser/{id}")
    public Mono<ResponseEntity<Object>> deleteUser(@PathVariable Long id) {
        return usuarioService.deleteUser(id)
                .thenReturn(new ResponseEntity<>(HttpStatus.OK))
                .onErrorResume(ResponseStatusException.class, e ->
                        Mono.just(new ResponseEntity<>(e.getStatusCode())));

    }
}