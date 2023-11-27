package es.soincon.proyecto.service;

import java.util.List;

import es.soincon.proyecto.dtos.PlayerDto;
import es.soincon.proyecto.dtos.SigningDto;
import es.soincon.proyecto.dtos.TeamDto;

/**
 * La interfaz IDataLoader define métodos para cargar datos en forma de DTOs.
 * Los métodos en esta interfaz serán implementados en una clase de servicio.
 */
public interface IDataLoader {

	// Método para cargar datos de jugadores en forma de DTOs.
	List<PlayerDto> loadPlayers();

	// Método para cargar datos de equipos en forma de DTOs.
	List<TeamDto> loadTeams();

	// Método para cargar datos de fichajes en forma de DTOs.
	List<SigningDto> loadSignings();
}
