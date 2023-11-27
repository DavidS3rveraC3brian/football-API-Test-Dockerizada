package es.soincon.proyecto.service;

import java.util.List;

import es.soincon.proyecto.dtos.SigningDto;

/**
 * La interfaz ISigningService define métodos para realizar operaciones relacionadas con fichajes (signings).
 * Estos métodos serán implementados en una clase de servicio.
 */
public interface ISigningService {
    
    /**
     * Crea nuevos fichajes a partir de una lista de DTOs.
     *
     * @param signingDto La lista de DTOs de fichajes a crear.
     * @return La lista de DTOs de fichajes creados.
     */
    List<SigningDto> createSigning(List<SigningDto> signingDto);
    
    /**
     * Obtiene todos los fichajes en forma de DTOs.
     *
     * @return La lista de DTOs de fichajes.
     */
    List<SigningDto> getSignings();
    
    /**
     * Obtiene los detalles de un fichaje en forma de DTO.
     *
     * @param id El ID del fichaje a obtener.
     * @return El DTO de fichaje con los detalles correspondientes.
     */
    SigningDto getSigning(Long id);
    
    /**
     * Obtiene un fichaje por su ID.
     *
     * @param id El ID del fichaje a obtener.
     * @return El DTO de fichaje correspondiente.
     */
    SigningDto findById(Long id);
    
    /**
     * Elimina un fichaje por su ID.
     *
     * @param id El ID del fichaje a eliminar.
     */
    void deleteSigning(Long id);
    
    /**
     * Actualiza los detalles de un fichaje a partir de un DTO.
     *
     * @param id El ID del fichaje a actualizar.
     * @param signingDto El DTO con los nuevos detalles del fichaje.
     * @return El DTO de fichaje actualizado.
     */
    SigningDto updateSigning(Long id, SigningDto signingDto);
}
