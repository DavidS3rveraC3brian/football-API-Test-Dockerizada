package es.soincon.proyecto.service;

import java.util.List;

import es.soincon.proyecto.dtos.DireccionDto;

/**
 * La interfaz IDireccionService define métodos para realizar operaciones relacionadas con Direcciones(direcciones).
 * Estos métodos serán implementados en una clase de servicio.
 */

public interface IDireccionService {
	
    /**
     * Crea nuevas direcciones a partir de una lista de DTOs.
     *
     * @param direccionesDto La lista de DTOs de direcciones a crear.
     * @return La lista de DTOs de las direcciones creadas.
     */
	List<DireccionDto> createDireccion(List<DireccionDto> direccionDto);
	
	/**
     * Obtiene todas las direcciones en forma de DTOs.
     *
     * @return La lista de DTOs de fichajes.
     */
    List<DireccionDto> getDirecciones();
    
    /**
     * Obtiene los detalles de una direccion en forma de DTO.
     *
     * @param id El ID de la direccion a obtener.
     * @return El DTO de direccion con los detalles correspondientes.
     */
    DireccionDto getDireccion(Long id);
    
    /**
     * Obtiene una direccion por su ID.
     *
     * @param id El ID de la direccion a obtener.
     * @return El DTO de direccion correspondiente.
     */
    DireccionDto findById(Long id);
    
    void deleteDireccion(Long id);
    
    /**
     * Actualiza los detalles de una direccion a partir de un DTO.
     *
     * @param id El ID de la direccion a actualizar.
     * @param direccionDto El DTO con los nuevos detalles de la direccion.
     * @return El DTO de direccion actualizado.
     */
    DireccionDto updateDireccion(Long id, DireccionDto direccionDto);
}
