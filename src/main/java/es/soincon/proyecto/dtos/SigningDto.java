package es.soincon.proyecto.dtos;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La clase SigningDto es un DTO (Data Transfer Object) que representa la
 * estructura de datos para un fichaje. Contiene anotaciones de Lombok y
 * validaciones de datos.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SigningDto {
    
    // ID del fichaje
    private Long id;

    // ID del jugador (requerido)
    @NotNull
    private Long playerId;

    // ID del equipo (requerido)
    @NotNull
    private Long teamId;

    // Fecha de inicio del fichaje
    private LocalDate since;

    // Fecha de finalización del fichaje
    private LocalDate until;
}
