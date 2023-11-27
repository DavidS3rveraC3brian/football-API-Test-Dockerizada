package es.soincon.proyecto.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.soincon.proyecto.dtos.DireccionDto;
import es.soincon.proyecto.service.IDireccionService;

/**
 * El controlador DireccionController maneja las solicitudes HTTP relacionadas
 * con las direcciones de los jugadores.
 */

@RestController
@RequestMapping(path = "/Direccion")
public class DireccionController {

	// Campo encapsulado para el servicio IDireccionService
	private final IDireccionService direccionService;

	public DireccionController(IDireccionService direccionService) {
		this.direccionService = direccionService;
	}

	@GetMapping
	public ResponseEntity<List<DireccionDto>> getAllDirecciones() {
		// retorna la lista de todos las direcciones en formato JSON
		return ResponseEntity.ok(direccionService.getDirecciones());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DireccionDto> getDireccion(@PathVariable Long id) {
		// retorna una direccion especifica por su ID en formato JSON
		return ResponseEntity.ok(direccionService.getDireccion(id));
	}

	@PostMapping
	public ResponseEntity<List<DireccionDto>> createBatchDireccion(
			@RequestBody @Valid List<DireccionDto> direccionDto) {
		// Crea una lista de direcciones en formato JSON y devuelve la respuesta
		return ResponseEntity.status(HttpStatus.CREATED).body(direccionService.createDireccion(direccionDto));

	}

	@PutMapping("/{id}")
	public ResponseEntity<DireccionDto> updateDireccion(@PathVariable Long id,
			@RequestBody @Valid DireccionDto direccionDto) {
		// Actualiza un fichaje por su ID y devuelve el fichaje en formato JSON
		return ResponseEntity.ok(direccionService.updateDireccion(id, direccionDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDireccion(@PathVariable Long id) {
		// Elimina un fichaje por su ID y devuelve una respuesta con el estado en
		// formato JSON
		direccionService.deleteDireccion(id);
		return ResponseEntity.accepted().build();

	}
}
