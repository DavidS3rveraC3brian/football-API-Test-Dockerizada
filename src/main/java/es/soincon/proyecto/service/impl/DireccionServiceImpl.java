package es.soincon.proyecto.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import es.soincon.proyecto.assemblers.DireccionAssembler;
import es.soincon.proyecto.dtos.DireccionDto;
import es.soincon.proyecto.entity.Direccion;
import es.soincon.proyecto.entity.Player;
import es.soincon.proyecto.repository.IDireccionRepository;
import es.soincon.proyecto.repository.IPlayerRepository;
import es.soincon.proyecto.service.IDireccionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DireccionServiceImpl implements IDireccionService {

	private final IDireccionRepository direccionRepository;
	private final IPlayerRepository playerRepository;

	@Override
	@Transactional
	public List<DireccionDto> createDireccion(List<DireccionDto> direccionDto) {

		List<DireccionDto> dtoList = direccionDto;
		List<DireccionDto> auxDtoList = new ArrayList<>();

		for (int i = 0; i < dtoList.size(); i++) {

			Player player = new Player();
			DireccionDto x = dtoList.get(i);
			if (x.getPlayerId() == null) {

				player = null;
			} else {

				player = playerRepository.findById(x.getPlayerId()).orElse(null);

				if (player == null) {

					throw new EntityNotFoundException("Direccion");
				}
			}
			Direccion direccion = DireccionAssembler.buildEntityFromDto(x, player);
			direccion.setPlayer(player);

			direccionRepository.save(direccion);

			auxDtoList.add(DireccionAssembler.buildDtoFromEntity(direccion));
		}
		return auxDtoList;
	}

	@Override
	@Transactional
	public List<DireccionDto> getDirecciones() {
		return Optional.ofNullable(direccionRepository.findAll().stream().map(DireccionAssembler::buildDtoFromEntity)
				.collect(Collectors.toList())).orElse(Collections.emptyList());
	}

	@Override
	@Transactional
	public DireccionDto getDireccion(Long id) {

		Direccion direccion = direccionRepository.findById(id).orElse(null);

		if (direccion == null) {

			throw new EntityNotFoundException("Direccion");
		}
		return DireccionAssembler.buildDtoFromEntity(direccion);
	}

	@Override
	@Transactional
	public DireccionDto findById(Long id) {
		Assert.notNull(id, "id no debe ser null");
		Direccion direccion = direccionRepository.findById(id).get();
		return DireccionAssembler.buildDtoFromEntity(direccion);
		
	}

	@Override
	@Transactional
	public void deleteDireccion(Long id) {
		
		Direccion direccion = direccionRepository.findById(id).orElse(null);
		//Añadido para borrar player por su ID
		Player player = playerRepository.getReferenceById(id);
		if(direccion == null) {
			
			throw new EntityNotFoundException("Direccion");
		}
		//Añadido
		if(player == null) {
			
			throw new EntityNotFoundException("Player");
		}
		//Añadido hasta -> direccionRepository.deleteById(id);
		//Guarda en una lista direcciones de tipo direccion el valor contenido en el repositorio
		//buscando la Id del jugador por el metodo findByPlayer y obter su id con el metodo getId de Player por su objeto player
		List<Direccion> direcciones = direccionRepository.findByPlayerId(player.getId());
		//Despues recorre dicha lista con el metodo stream, busca en el repositorio con un bucle for each y almacena el valor obtenido
		//en la variable d, posteriormente llama al metodo delete de direccionRepository y borra el valor contenido en dicha variable(d)
		direcciones.stream().forEach(d -> direccionRepository.delete(d));
		//Finalmente borra el id de la direccion del repositorio.
		direccionRepository.deleteById(id);
		
	}

	@Override
	@Transactional
	public DireccionDto updateDireccion(Long id, DireccionDto direccionDto) {
		
		Assert.notNull(id, "id no puede ser null");
		Assert.notNull(direccionDto, "playerDto id no puede ser null");
		
		Player player = new Player();
		
		if(direccionDto.getPlayerId()==null) {
			
			player = null;
			
		} else {
			
			player = playerRepository.findById(direccionDto.getPlayerId()).orElse(null);
			
			if(player == null) {
				
				throw new EntityNotFoundException("Player");
			}
		}
		
		Direccion direccion = direccionRepository.findById(direccionDto.getPlayerId()).orElse(null);
		
		if(direccion == null) {
			
			throw new EntityNotFoundException("Direccion");
		}
		
		direccion = direccionRepository.saveAndFlush(DireccionAssembler.buildEntityFromDto(direccionDto, player));
		
		return DireccionAssembler.buildDtoFromEntity(direccion);
	}
}
