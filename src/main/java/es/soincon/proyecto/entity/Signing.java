package es.soincon.proyecto.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La clase Signing es una entidad que mapea una tabla de la base de datos.
 * Contiene anotaciones de Lombok y JPA para configurar la entidad y las relaciones.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indica que esta clase es una entidad mapeada a una tabla de la base de datos.
@Table(name = "Signing") // Especifica el nombre de la tabla en la base de datos.
public class Signing {
    
    @Id // Indica que este campo es la clave primaria de la tabla.
    @Column(name = "ID") // Especifica el nombre de la columna en la base de datos.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un valor autonumérico para la columna "id".
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con la entidad Player.
    @JoinColumn(name = "PLAYER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_SIGNINGS_PLAYER"))
    private Player player; // Objeto de tipo Player que representa el jugador del fichaje.
    
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con la entidad Team.
    @JoinColumn(name = "TEAM_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_SIGNINGS_TEAM"))
    private Team team; // Objeto de tipo Team que representa el equipo del fichaje.
    
    @Column(name = "SINCE") // Columna "since".
    private LocalDate since; // Fecha de inicio del fichaje.
    
    @Column(name = "UNTIL") // Columna "until".
    private LocalDate until; // Fecha de finalización del fichaje.
}
