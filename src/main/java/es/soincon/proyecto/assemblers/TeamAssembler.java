package es.soincon.proyecto.assemblers;

import org.springframework.beans.BeanUtils;

import es.soincon.proyecto.dtos.TeamDto;
import es.soincon.proyecto.entity.Team;

/**
 * La clase TeamAssembler se encarga de convertir objetos entre las clases
 * Team y TeamDto utilizando el método de copia de propiedades.
 */
public class TeamAssembler {

    // No se permite la instanciación de la clase, ya que todos los métodos son estáticos.
    private TeamAssembler() {
        throw new UnsupportedOperationException("No puede iniciarse la clase Assembler de Team");
    }

    /**
     * Convierte un objeto de la clase Team a un objeto de la clase TeamDto.
     *
     * @param team El objeto Team a ser convertido.
     * @return Un objeto TeamDto que representa los datos del equipo.
     */
    public static TeamDto buildDtoFromEntity(Team team) {
        TeamDto teamDto = new TeamDto();
        // Copia las propiedades del objeto Team al objeto TeamDto.
        BeanUtils.copyProperties(team, teamDto);
        return teamDto;
    }

    /**
     * Convierte un objeto de la clase TeamDto a un objeto de la clase Team.
     *
     * @param teamDto El objeto TeamDto a ser convertido.
     * @return Un objeto Team que representa los datos del equipo.
     */
    public static Team buildEntityFromDto(TeamDto teamDto) {
        Team team = new Team();
        // Copia las propiedades del objeto TeamDto al objeto Team.
        BeanUtils.copyProperties(teamDto, team);
        return team;
    }
}
