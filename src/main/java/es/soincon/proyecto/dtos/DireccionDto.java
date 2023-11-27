package es.soincon.proyecto.dtos;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La clase DireccionDto es un DTO que representa la estructura de las
 * direcciones para un juegador. Contiene anotaciones de Lombok y validaciones
 * de datos.
 */

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DireccionDto {

	// ID autonumerico
	private Long id;

	// FK de player
	@NotNull
	private Long playerId;

	// calle
	private String calle;

	// numero
	private Short numero;

	// piso
	private Short piso;

	// puerta
	private String puerta;
}
