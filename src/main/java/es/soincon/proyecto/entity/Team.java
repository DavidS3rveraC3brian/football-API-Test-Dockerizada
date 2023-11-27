package es.soincon.proyecto.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * La clase Team es una entidad que mapea una tabla de la base de datos.
 * Contiene anotaciones de Lombok y JPA para configurar la entidad y las relaciones.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indica que esta clase es una entidad mapeada a una tabla de la base de datos.
@Table(name = "Team") // Especifica el nombre de la tabla en la base de datos.
public class Team {
    
    @Id // Indica que este campo es la clave primaria de la tabla.
    @Column(name = "ID") // Especifica el nombre de la columna en la base de datos.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un valor autonumérico para la columna "id".
    private Long id;
    
    @Column(name = "NAME") // Columna "name".
    private String name; // Nombre del equipo.
    
    @Column(name = "EMAIL") // Columna "email".
    private String email; // Correo electrónico del equipo.
    
    @Column(name = "SINCE") // Columna "since".
    private LocalDate since; // Fecha de creación del equipo.
    
    @Column(name = "CITY") // Columna "city".
    private String city; // Ciudad del equipo.
    
    @EqualsAndHashCode.Exclude // Excluye estos campos en las comparaciones de igualdad y hash.
    @ToString.Exclude // Excluye estos campos en el método toString.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", orphanRemoval = false)
    private Set<Signing> team = new HashSet<Signing>(); // Relación con la entidad Signing.
}
