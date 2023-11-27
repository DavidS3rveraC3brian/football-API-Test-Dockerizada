package es.soincon.proyecto.entity;

import java.math.BigDecimal;
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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * La clase Player es una entidad que mapea una tabla de la base de datos.
 * Contiene anotaciones de Lombok y JPA para configurar la entidad.
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // Indica que esta clase es una entidad mapeada a una tabla de la base de datos.
@Table(name = "Player") // Especifica el nombre de la tabla en la base de datos.
public class Player {
    
    @Id // Indica que este campo es la clave primaria de la tabla.
    @Column(name = "ID") // Especifica el nombre de la columna en la base de datos.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un valor autonumérico para la columna "id".
    private Long id;
    
    @Column(name = "FIRSTNAME") // Columna "firstname".
    private String firstname;
    
    @Column(name = "LASTNAME") // Columna "lastname".
    private String lastname;
    
    @Column(name = "EMAIL") // Columna "email".
    private String email;
    
    @Column(name = "BIRTHDATE") // Columna "birthdate".
    private LocalDate birthdate;
    
    @Column(name = "POSITION") // Columna "position".
    private String position;
    
    @Column(name = "GENDER") // Columna "gender".
    private String gender;
    
    @Column(name = "WEIGHT") // Columna "weight".
    private Long weight;
    
    @Column(name = "HIGH") // Columna "high".
    private Long high;
    
    @Column(name = "IMC") // Columna "imc".
    private BigDecimal imc;
    
    @Column(name = "FAT") // Columna "fat".
    private Long fat;
    
    @EqualsAndHashCode.Exclude // Excluye estos campos en las comparaciones de igualdad y hash.
    @ToString.Exclude // Excluye estos campos en el método toString.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player", orphanRemoval = false)
    private Set<Signing> player = new HashSet<Signing>(); // Relación con la entidad Signing.
    
    @EqualsAndHashCode.Exclude // Excluye estos campos en las comparaciones de igualdad y hash.
    @ToString.Exclude // Excluye estos campos en el método toString.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player", orphanRemoval = false)
    private Set<Direccion> playerDireccion = new HashSet<Direccion>(); // Relación con la entidad Direccion.
}
