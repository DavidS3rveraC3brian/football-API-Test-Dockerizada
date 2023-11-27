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

import es.soincon.proyecto.dtos.PlayerDto;
import es.soincon.proyecto.service.IPlayerService;

/**
 * El controlador PlayerController maneja las solicitudes HTTP relacionadas con
 * los jugadores.
 */
@RestController
@RequestMapping(path = "/player")
public class PlayerController {

    private final IPlayerService playerService;

    /**
     * Constructor que recibe una instancia de IPlayerService como parámetro.
     *
     * @param playerService Instancia de IPlayerService para manejar la lógica de los jugadores.
     */
    public PlayerController(IPlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayer() {
        // Retorna la lista de todos los jugadores en formato JSON.
        return ResponseEntity.ok(playerService.getPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable Long id) {
        // Retorna un jugador específico por su ID en formato JSON.
        return ResponseEntity.ok(playerService.getAtribute(id));
    }

    @GetMapping("/search-by-name/{name}")
    public ResponseEntity<List<PlayerDto>> getPlayerByName(@PathVariable String name) {
        // Retorna una lista de jugadores que coinciden con el nombre proporcionado en formato JSON.
        return ResponseEntity.ok(playerService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<List<PlayerDto>> createBatchPlayer(@RequestBody @Valid List<PlayerDto> playerDto) {
        // Crea una lista de jugadores en formato JSON y devuelve la respuesta con el estado creado.
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.createPlayer(playerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable Long id, @Valid @RequestBody PlayerDto playerDto) {
        // Actualiza un jugador por su ID y devuelve el jugador actualizado en formato JSON.
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        // Elimina un jugador por su ID y devuelve una respuesta con el estado aceptado (202).
        playerService.deletePlayer(id);
        return ResponseEntity.accepted().build();
    }
}
