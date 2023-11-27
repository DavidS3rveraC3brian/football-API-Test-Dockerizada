package es.soincon.proyecto.assemblers;

import org.springframework.beans.BeanUtils;

import es.soincon.proyecto.dtos.DireccionDto;
import es.soincon.proyecto.entity.Direccion;
import es.soincon.proyecto.entity.Player;

/**
 * La clase DireccionAssembler se encarga de convertir objetos entre las clases
 * Direccion y DireccionDto utilizando el método de copia de propiedades.
 */

public class DireccionAssembler {

	private DireccionAssembler() {
		throw new UnsupportedOperationException("No puede iniciarse la clase Assembler de Direccion");
	}

	/**
	 * Convierte un objeto de la clase Direccion a un objeto de la clase
	 * DireccionDto.
	 *
	 * @param direccion El objeto Direccion a ser convertido.
	 * @return Un objeto direccionDto que representa los datos de la firma.
	 */
	public static DireccionDto buildDtoFromEntity(Direccion direccion) {
		DireccionDto direccionDto = new DireccionDto();

		// Copia las propiedades del objeto Direccion al DireccionDto
		BeanUtils.copyProperties(direccion, direccionDto, "player");

		// Establece los ID de Player a las direcciones
		direccionDto.setPlayerId(direccion.getPlayer().getId());

		return direccionDto;
	}

	/**
	 * Convierte un objeto de la clase DireccionDto a un objeto de la clase
	 * Direccion.
	 *
	 * @param direccionDto El objeto DireccionDto a ser convertido.
	 * @param player       El objeto Player relacionado con la Direccion.
	 * @return Un objeto Direccion que representa la direccion con los datos
	 *         proporcionados.
	 */
	public static Direccion buildEntityFromDto(DireccionDto direccionDto, Player player) {

		Direccion direccion = new Direccion();
		// Copia las propiedades del objeto DireccionDto al objeto Direccion
		BeanUtils.copyProperties(direccionDto, direccion);

		// Establece el jugador y la direccion del objeto Direccion
		direccion.setPlayer(player);

		return direccion;
	}
}
