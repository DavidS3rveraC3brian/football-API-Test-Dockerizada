package es.soincon.proyecto.assemblers;

import org.springframework.beans.BeanUtils;

import es.soincon.proyecto.dtos.SigningDto;
import es.soincon.proyecto.entity.Player;
import es.soincon.proyecto.entity.Signing;
import es.soincon.proyecto.entity.Team;

/**
 * La clase SigningAssembler se encarga de convertir objetos entre las clases
 * Signing y SigningDto utilizando el método de copia de propiedades.
 */
public class SigningAssembler {

    // No se permite la instanciación de la clase, ya que todos los métodos son estáticos.
    private SigningAssembler() {
        throw new UnsupportedOperationException("No puede iniciarse la clase Assembler de Signing");
    }

    /**
     * Convierte un objeto de la clase Signing a un objeto de la clase SigningDto.
     *
     * @param signing El objeto Signing a ser convertido.
     * @return Un objeto SigningDto que representa los datos de la firma.
     */
    public static SigningDto buildDtoFromEntity(Signing signing) {
        SigningDto signingDto = new SigningDto();
        // Copia las propiedades del objeto Signing al objeto SigningDto, excluyendo "player" y "team".
        BeanUtils.copyProperties(signing, signingDto, "player", "team");

        // Establece los IDs de jugador y equipo en el SigningDto.
        signingDto.setPlayerId(signing.getPlayer().getId());
        signingDto.setTeamId(signing.getTeam().getId());
        return signingDto;
    }

    /**
     * Convierte un objeto de la clase SigningDto a un objeto de la clase Signing.
     *
     * @param signingDto El objeto SigningDto a ser convertido.
     * @param player     El objeto Player relacionado con la firma.
     * @param team       El objeto Team relacionado con la firma.
     * @return Un objeto Signing que representa la firma con los datos proporcionados.
     */
    public static Signing buildEntityFromDto(SigningDto signingDto, Player player, Team team) {
        Signing signing = new Signing();
        // Copia las propiedades del objeto SigningDto al objeto Signing.
        BeanUtils.copyProperties(signingDto, signing);

        // Establece el jugador y el equipo en el objeto Signing.
        signing.setPlayer(player);
        signing.setTeam(team);

        return signing;
    }
}
