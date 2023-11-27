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
 * La clase TeamDto es un DTO (Data Transfer Object) que representa la
 * estructura de datos para un equipo. Contiene anotaciones de Lombok y
 * validaciones de datos.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {
    
    // ID del equipo
    private Long id;

    // Nombre del equipo (requerido)
    @NotNull
    private String name;

    // Correo electrónico del equipo
    private String email;

    // Fecha de creación del equipo
    private LocalDate since;

    // Ciudad del equipo
    private String city;
}
