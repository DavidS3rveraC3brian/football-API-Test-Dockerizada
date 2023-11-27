package es.soincon.proyecto.assemblers;

import org.springframework.beans.BeanUtils;

import es.soincon.proyecto.dtos.PlayerDto;
import es.soincon.proyecto.dtos.PlayerMinDto;
import es.soincon.proyecto.entity.Player;

/**
 * La clase PlayerAssembler se encarga de convertir objetos entre las clases
 * Player y PlayerDto utilizando el método de copia de propiedades.
 */
public class PlayerAssembler {

	// No se permite la instanciación de la clase, ya que todos los métodos son
	// estáticos.
	private PlayerAssembler() {
		throw new UnsupportedOperationException("No puede iniciarse la clase Assembler de Player");
	}

	/**
	 * Convierte un objeto de la clase Player a un objeto de la clase PlayerDto.
	 *
	 * @param player El objeto Player a ser convertido.
	 * @return Un objeto PlayerDto que representa los datos del jugador
	 */
	public static PlayerDto buildDtoFromEntity(Player player) {
		if (player == null) {
			System.out.println("la entidad esta en null");
			return null;
		}

		PlayerDto playerDto = new PlayerDto();
		// Copia las propiedades del objeto Player al objeto PlayerDto.
		BeanUtils.copyProperties(player, playerDto);
		return playerDto;
	}

	// Assembler para construir un Dto con el unico campo "firstname"
	public static PlayerMinDto buildMinDtoFromEntity(Player player) {
		PlayerMinDto playerMinDto = new PlayerMinDto();
		BeanUtils.copyProperties(player, playerMinDto, "id", "lastname", "email", "birthdate", "position", "gender",
				"weight", "high", "imc", "fat");

		return playerMinDto;
	}

	/**
	 * Convierte un objeto de la clase PlayerDto a un objeto de la clase Player.
	 *
	 * @param playerDto El objeto PlayerDto a ser convertido.
	 * @return Un objeto Player que representa los datos del jugador.
	 */
	public static Player buildEntityFromDto(PlayerDto playerDto) {
		Player player = new Player();
		// Copia las propiedades del objeto PlayerDto al objeto Player.
		BeanUtils.copyProperties(playerDto, player);
		return player;
	}
}
