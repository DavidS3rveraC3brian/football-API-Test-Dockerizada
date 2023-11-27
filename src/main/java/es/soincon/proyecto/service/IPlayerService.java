package es.soincon.proyecto.service;

import java.util.List;

import es.soincon.proyecto.dtos.PlayerDto;
import es.soincon.proyecto.dtos.PlayerMinDto;

/**
 * La interfaz IPlayerService define métodos para realizar operaciones relacionadas con jugadores.
 * Estos métodos serán implementados en una clase de servicio.
 */
public interface IPlayerService {

    /**
     * Crea nuevos jugadores a partir de una lista de DTOs.
     *
     * @param playerDto La lista de DTOs de jugadores a crear.
     * @return La lista de DTOs de jugadores creados.
     */
    List<PlayerDto> createPlayer(List<PlayerDto> playerDto);
    
    /**
     * Obtiene todos los jugadores en forma de DTOs.
     *
     * @return La lista de DTOs de jugadores.
     */
    List<PlayerDto> getPlayers();
    
    /**
     * Obtiene los detalles de un jugador en forma de DTO.
     *
     * @param id El ID del jugador a obtener.
     * @return El DTO de jugador con los detalles correspondientes.
     */
    PlayerDto getAtribute(Long id);
    
    /**
     * Elimina un jugador por su ID.
     *
     * @param id El ID del jugador a eliminar.
     */
    void deletePlayer(Long id);
    
    /**
     * Actualiza los detalles de un jugador a partir de un DTO.
     *
     * @param id El ID del jugador a actualizar.
     * @param playerDto El DTO con los nuevos detalles del jugador.
     * @return El DTO de jugador actualizado.
     */
    PlayerDto updatePlayer(Long id, PlayerDto playerDto);
    
    /**
     * Busca jugadores por su nombre.
     *
     * @param firstname El nombre del jugador a buscar.
     * @return La lista de DTOs de jugadores que coinciden con el nombre.
     */
    List<PlayerDto> findByName(String firstname);

	List<PlayerMinDto> findMinDto(Long id, String firstname);
}
