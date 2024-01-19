package com.mx.orquestador.model;

import com.mx.orquestador.validations.MessageValid;
import com.mx.orquestador.validations.Validaciones;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuarioorq {
    @NotNull(message = MessageValid.BLANK_ID_MESSAGE)
    private Long id;
    @NotBlank(message = MessageValid.BLANK_NAME_MESSAGE)
    private String nombre;

    @NotBlank(message = MessageValid.BLANK_EMAIL_MESSAGE)
    @Email(regexp = Validaciones.EMAIL_PATTERN, message = MessageValid.INVALID_EMAIL_FORMAT_MESSAGE)
    private String email;

    @NotBlank(message = MessageValid.BLANK_PHONE_MESSAGE)
    @Pattern(regexp = Validaciones.PHONE_PATTERN, message = MessageValid.INVALID_PHONE_FORMAT_MESSAGE)
    private String telefono;

    @NotBlank(message = MessageValid.BLANK_CURP_MESSAGE)
    @Pattern(regexp = Validaciones.CURP_PATTERN, message = MessageValid.INVALID_CURP_FORMAT_MESSAGE)
    private String curp;

    @NotBlank(message = MessageValid.BLANK_POSTAL_CODE_MESSAGE)
    @Pattern(regexp = Validaciones.POSTAL_CODE_PATTERN, message = MessageValid.INVALID_POSTAL_CODE_FORMAT_MESSAGE)
    private String codigoPostal;
}
