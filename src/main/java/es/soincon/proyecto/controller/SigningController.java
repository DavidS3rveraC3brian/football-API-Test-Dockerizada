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

import es.soincon.proyecto.dtos.SigningDto;
import es.soincon.proyecto.service.ISigningService;

/**
 * El controlador SigningController maneja las solicitudes HTTP relacionadas
 * con los fichajes.
 */
@RestController
@RequestMapping(path = "/Signing")
public class SigningController {

    // Campo encapsulado para el servicio ISigningService
    private final ISigningService signingService;

    /**
     * Constructor que recibe una instancia de ISigningService como parámetro.
     *
     * @param signingService Instancia de ISigningService para manejar la lógica de los fichajes.
     */
    public SigningController(ISigningService signingService) {
        this.signingService = signingService;
    }

    @GetMapping
    public ResponseEntity<List<SigningDto>> getAllSigning() {
        // Retorna la lista de todos los fichajes en formato JSON.
        return ResponseEntity.ok(signingService.getSignings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SigningDto> getSigning(@PathVariable Long id) {
        // Retorna un fichaje específico por su ID en formato JSON.
        return ResponseEntity.ok(signingService.getSigning(id));
    }

    @PostMapping
    public ResponseEntity<List<SigningDto>> createBatchSigning(@RequestBody @Valid List<SigningDto> signingDto) {
        // Crea una lista de fichajes en formato JSON y devuelve la respuesta con el estado creado.
        return ResponseEntity.status(HttpStatus.CREATED).body(signingService.createSigning(signingDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SigningDto> updateSigning(@PathVariable Long id, @RequestBody @Valid SigningDto signingDto) {
        // Actualiza un fichaje por su ID y devuelve el fichaje actualizado en formato JSON.
        return ResponseEntity.ok(signingService.updateSigning(id, signingDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSigning(@PathVariable Long id) {
        // Elimina un fichaje por su ID y devuelve una respuesta con el estado aceptado (202).
        signingService.deleteSigning(id);
        return ResponseEntity.accepted().build();
    }
}
