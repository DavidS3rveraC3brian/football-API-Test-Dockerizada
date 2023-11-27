package es.soincon.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.soincon.proyecto.entity.Direccion;

/**
 * El repositorio IDireccionRepository es una interfaz que define métodos para
 * interactuar con la entidad Direccion en la base de datos.
 */

@Repository // componente de repositorio
@Transactional(propagation = Propagation.MANDATORY) // propagacion de clave
public interface IDireccionRepository extends JpaRepository<Direccion, Long> {

	// Metodo para buscar direccion por ID del jugador
	List<Direccion> findByPlayerId(Long playerId);

}
