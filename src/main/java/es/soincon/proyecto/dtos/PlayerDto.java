package es.soincon.proyecto.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La clase PlayerDto es un DTO (Data Transfer Object) que representa la
 * estructura de datos para un jugador. Contiene anotaciones de Lombok y
 * validaciones de datos.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDto {

    // ID del jugador
    private Long id;

    // Nombre del jugador (requerido)
    @NotNull
    private String firstname;

    // Apellido del jugador
    private String lastname;

    // Correo electrónico del jugador
    private String email;

    // Fecha de nacimiento del jugador
    private LocalDate birthdate;

    // Posición del jugador en el equipo
    private String position;

    // Género del jugador
    private String gender;

    // Peso del jugador
    private Long weight;

    // Altura del jugador
    private Long high;

    // Índice de masa corporal (IMC) del jugador
    private BigDecimal imc;

    // Porcentaje de grasa del jugador
    private Long fat;
}
