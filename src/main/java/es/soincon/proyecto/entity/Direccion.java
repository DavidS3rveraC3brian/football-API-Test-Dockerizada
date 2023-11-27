package es.soincon.proyecto.entity;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La clase Direccion es una entidad que mapea una tabla de la base de datos.
 * Contiene anotaciones de Lombok y JPA para configurar la entidad.
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indica que esta clase es una entidad mapeada a una tabla de la base de datos.
@Table(name = "Direccion") // Especifica el nombre de la tabla en la base de datos.
public class Direccion {

	 	@Id // Indica que este campo es la clave primaria de la tabla.
	    @Column(name = "ID") // Especifica el nombre de la columna en la base de datos.
	    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un valor autonumérico para la columna "id".
	    private Long id;
	    
	 	@ManyToOne(fetch = FetchType.LAZY)
	 	@JoinColumn(name = "PLAYER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_DIRECCION_PLAYER"))
	 	private Player player; 
		
		// calle
	 	@Column(name = "CALLE")
		private String calle;

		// numero
	 	@Column(name = "NUMERO")
		private Short numero;

		// piso
	 	@Column(name = "PISO")
		private Short piso;

		// puerta
	 	@Column(name = "PUERTA")
		private String puerta;
}
