package es.soincon.proyecto.service;

import java.util.List;

import es.soincon.proyecto.dtos.TeamDto;

/**
 * La interfaz ITeamService define métodos para realizar operaciones relacionadas con equipos.
 * Estos métodos serán implementados en una clase de servicio.
 */
public interface ITeamService {

    /**
     * Crea nuevos equipos a partir de una lista de DTOs.
     *
     * @param teamDto La lista de DTOs de equipos a crear.
     * @return La lista de DTOs de equipos creados.
     */
    List<TeamDto> createTeam(List<TeamDto> teamDto);
    
    /**
     * Obtiene todos los equipos en forma de DTOs.
     *
     * @return La lista de DTOs de equipos.
     */
    List<TeamDto> getTeams();
    
    /**
     * Obtiene los detalles de un equipo en forma de DTO.
     *
     * @param id El ID del equipo a obtener.
     * @return El DTO de equipo con los detalles correspondientes.
     */
    TeamDto getAtribute(Long id);
    
    /**
     * Elimina un equipo por su ID.
     *
     * @param id El ID del equipo a eliminar.
     */
    void deleteTeam(Long id);
    
    /**
     * Actualiza los detalles de un equipo a partir de un DTO.
     *
     * @param id El ID del equipo a actualizar.
     * @param teamDto El DTO con los nuevos detalles del equipo.
     * @return El DTO de equipo actualizado.
     */
    TeamDto updateTeam(Long id, TeamDto teamDto);
    
    /**
     * Busca un equipo por su nombre.
     *
     * @param name El nombre del equipo a buscar.
     * @return El DTO de equipo que coincide con el nombre.
     */
    List<TeamDto> findByName(String name);
}
