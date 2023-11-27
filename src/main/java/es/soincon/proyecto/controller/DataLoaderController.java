package es.soincon.proyecto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.soincon.proyecto.dtos.PlayerDto;
import es.soincon.proyecto.dtos.SigningDto;
import es.soincon.proyecto.dtos.TeamDto;
import es.soincon.proyecto.service.IDataLoader;

/**
 * El controlador DataLoaderController maneja las solicitudes HTTP relacionadas
 * con la carga de datos desde archivos CSV para jugadores, equipos y fichajes.
 */
@RestController
@RequestMapping(path = "/data-controller")
public class DataLoaderController {

    // Campo encapsulado para el servicio IDataLoader
    private final IDataLoader dataLoader;

    /**
     * Constructor que recibe una instancia de IDataLoader como parámetro.
     *
     * @param dataLoader Instancia de IDataLoader para cargar los datos.
     */
    public DataLoaderController(IDataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    /**
     * Maneja la solicitud GET para cargar datos de jugadores desde un archivo CSV.
     *
     * @return Respuesta ResponseEntity con una lista de objetos PlayerDto cargados.
     */
    @GetMapping(path = "/player-loader")
    public ResponseEntity<List<PlayerDto>> cargarDatosDesdeCSVPlayers() {
        return ResponseEntity.ok(dataLoader.loadPlayers());
    }

    /**
     * Maneja la solicitud GET para cargar datos de equipos desde un archivo CSV.
     *
     * @return Respuesta ResponseEntity con una lista de objetos TeamDto cargados.
     */
    @GetMapping(path = "/team-loader")
    public ResponseEntity<List<TeamDto>> cargarDatosDesdeCSVTeams() {
        return ResponseEntity.ok(dataLoader.loadTeams());
    }

    /**
     * Maneja la solicitud GET para cargar datos de fichajes desde un archivo CSV.
     *
     * @return Respuesta ResponseEntity con una lista de objetos SigningDto cargados.
     */
    @GetMapping(path = "/signing-loader")
    public ResponseEntity<List<SigningDto>> cargarDatosDesdeCSVSignings() {
        return ResponseEntity.ok(dataLoader.loadSignings());
    }
}
