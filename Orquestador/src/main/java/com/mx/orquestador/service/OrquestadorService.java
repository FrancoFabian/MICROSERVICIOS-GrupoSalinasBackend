package com.mx.orquestador.service;

import com.mx.orquestador.model.UserCreate;
import com.mx.orquestador.model.Usuarioorq;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class OrquestadorService {

    private WebClient webClient;
    public OrquestadorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8016/api/Usuarios").build();
    }


    public Mono<Usuarioorq> buscarPorEmail(String email) {
        return webClient.get()
                .uri("/buscarPorEmail?email=" + email)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.equals(HttpStatus.NOT_FOUND), clientResponse -> Mono.empty()) // Manejar 404 correctamente
                .bodyToMono(Usuarioorq.class)
                .onErrorResume(e -> {
                    // Logear o manejar el error
                    return Mono.empty(); // Retorna Mono vacío en caso de error
                });
    }
    public Mono<Usuarioorq> buscarPorCurp(String curp) {
        return webClient.get()
                .uri("/buscarPorCurp?curp=" + curp)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.equals(HttpStatus.NOT_FOUND), clientResponse -> Mono.empty()) // Manejar 404 correctamente
                .bodyToMono(Usuarioorq.class)
                .onErrorResume(e -> {
                    // Logear o manejar el error
                    return Mono.empty(); // Retorna Mono vacío en caso de error
                });
    }
    public Mono<Usuarioorq> buscarPorId(Long id) {
        return webClient.get()
                .uri("/buscarPorId?id=" + id)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.equals(HttpStatus.NOT_FOUND), clientResponse -> Mono.empty()) // Manejar 404 correctamente
                .bodyToMono(Usuarioorq.class)
                .onErrorResume(e -> {
                    // Logear o manejar el error
                    return Mono.empty(); // Retorna Mono vacío en caso de error
                });
    }



    public Mono<Usuarioorq> crearUsuario(UserCreate userCreate) {
        Mono<Boolean> emailExists = buscarPorEmail(userCreate.getEmail())
                .map(user -> true)
                .defaultIfEmpty(false);

        Mono<Boolean> curpExists = buscarPorCurp(userCreate.getCurp())
                .map(user -> true)
                .defaultIfEmpty(false);

        return Mono.zip(emailExists, curpExists)
                .doOnNext(results -> System.out.println("Resultados de la búsqueda: Email existe - " + results.getT1() + ", CURP existe - " + results.getT2()))
                .flatMap(results -> {
                    boolean emailAlreadyExists = results.getT1();
                    boolean curpAlreadyExists = results.getT2();

                    if (emailAlreadyExists && curpAlreadyExists) {
                        System.out.println("El email y la CURP ya existen.");
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "El email y la CURP ya existen."));
                    } else if (emailAlreadyExists) {
                        System.out.println("El email ya existe.");
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "El email ya existe."));
                    } else if (curpAlreadyExists) {
                        System.out.println("La CURP ya existe.");
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "La CURP ya existe."));
                    } else {
                        System.out.println("Creando usuario con email: " + userCreate.getEmail() + " y CURP: " + userCreate.getCurp());
                        return webClient.post()
                                .uri("/crear")
                                .bodyValue(userCreate)
                                .retrieve()
                                .bodyToMono(Usuarioorq.class);
                    }
                });
    }


    public Mono<Usuarioorq> actualizarUsuario(Usuarioorq userUpdate) {
        return buscarPorId(userUpdate.getId())
                .flatMap(existingUser -> {
                    boolean isSameEmail = existingUser.getEmail().equals(userUpdate.getEmail());
                    boolean isSameCurp = existingUser.getCurp().equals(userUpdate.getCurp());

                    if (isSameEmail && isSameCurp) {
                        // Actualizar directamente si el email y la CURP son los mismos
                        return webClient.post()
                                .uri("/update")
                                .bodyValue(userUpdate)
                                .retrieve()
                                .bodyToMono(Usuarioorq.class);
                    } else {
                        // Verificar si el email o la CURP existen en otros usuarios
                        Mono<Boolean> emailExists = isSameEmail ? Mono.just(false) : buscarPorEmail(userUpdate.getEmail()).hasElement();
                        Mono<Boolean> curpExists = isSameCurp ? Mono.just(false) : buscarPorCurp(userUpdate.getCurp()).hasElement();

                        return Mono.zip(emailExists, curpExists)
                                .flatMap(results -> {
                                    boolean emailAlreadyExists = results.getT1();
                                    boolean curpAlreadyExists = results.getT2();

                                    if (emailAlreadyExists) {
                                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "El email ya existe en otro usuario."));
                                    } else if (curpAlreadyExists) {
                                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "La CURP ya existe en otro usuario."));
                                    } else {
                                        // Si no existen conflictos, actualiza el usuario
                                        return webClient.post()
                                                .uri("/update")
                                                .bodyValue(userUpdate)
                                                .retrieve()
                                                .bodyToMono(Usuarioorq.class);
                                    }
                                });
                    }
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado para actualizar")));
    }


}
