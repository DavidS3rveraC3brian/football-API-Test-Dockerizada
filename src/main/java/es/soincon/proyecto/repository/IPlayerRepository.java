package es.soincon.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.soincon.proyecto.entity.Player;

/**
 * El repositorio IPlayerRepository es una interfaz que define métodos para interactuar
 * con la entidad Player en la base de datos.
 */
@Repository // Indica que esta interfaz es un componente de repositorio.
@Transactional(propagation = Propagation.MANDATORY) // Define la configuración de transacciones.
public interface IPlayerRepository extends JpaRepository<Player, Long> {
    
    // Método para buscar jugadores por su nombre.
    List<Player> findByFirstname(String firstname);
}
