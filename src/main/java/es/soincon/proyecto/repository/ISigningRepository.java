package es.soincon.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.soincon.proyecto.entity.Signing;

/**
 * El repositorio ISigningRepository es una interfaz que define métodos para interactuar
 * con la entidad Signing en la base de datos.
 */
@Repository // Indica que esta interfaz es un componente de repositorio.
@Transactional(propagation = Propagation.MANDATORY) // Define la configuración de transacciones.
public interface ISigningRepository extends JpaRepository<Signing, Long> {
    
    // Método para buscar fichajes por ID del jugador.
    List<Signing> findByPlayerId(Long playerId);
    
    // Método para buscar fichajes por ID del equipo.
    List<Signing> findByTeamId(Long teamId);
    
}
