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

import es.soincon.proyecto.dtos.TeamDto;
import es.soincon.proyecto.service.ITeamService;

/**
 * El controlador TeamController maneja las solicitudes HTTP relacionadas con los equipos.
 */
@RestController
@RequestMapping(path = "/team")
public class TeamController {

    // Campo encapsulado para el servicio ITeamService
    private final ITeamService teamService;

    /**
     * Constructor que recibe una instancia de ITeamService como parámetro.
     *
     * @param teamService Instancia de ITeamService para manejar la lógica de los equipos.
     */
    public TeamController(ITeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
    	return ResponseEntity.ok(teamService.getTeams());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable Long id) {
        // Retorna un equipo específico por su ID en formato JSON.
        return ResponseEntity.ok(teamService.getAtribute(id));
    }

    @PostMapping
    public ResponseEntity<List<TeamDto>> createBatchTeam(@RequestBody @Valid List<TeamDto> teamDto) {
        // Crea una lista de equipos en formato JSON y devuelve la respuesta con el estado creado.
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(teamDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @RequestBody @Valid TeamDto teamDto) {
        // Actualiza un equipo por su ID y devuelve el equipo actualizado en formato JSON.
        return ResponseEntity.ok(teamService.updateTeam(id, teamDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        // Elimina un equipo por su ID y devuelve una respuesta con el estado aceptado (202).
        teamService.deleteTeam(id);
        return ResponseEntity.accepted().build();
    }
}
