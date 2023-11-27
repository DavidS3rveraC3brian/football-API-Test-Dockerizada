package es.soincon.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.soincon.proyecto.entity.Team;

/**
 * El repositorio ITeamRepository es una interfaz que define métodos para interactuar
 * con la entidad Team en la base de datos.
 */
@Repository // Indica que esta interfaz es un componente de repositorio.
@Transactional(propagation = Propagation.MANDATORY) // Define la configuración de transacciones.
public interface ITeamRepository extends JpaRepository<Team, Long> {
    
    // Método para buscar equipos por su nombre.
    List<Team> findByName(String name);
}
