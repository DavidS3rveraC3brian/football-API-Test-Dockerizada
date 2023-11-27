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

import es.soincon.proyecto.assemblers.PlayerAssembler;
import es.soincon.proyecto.dtos.PlayerDto;
import es.soincon.proyecto.dtos.PlayerMinDto;
import es.soincon.proyecto.entity.Player;
import es.soincon.proyecto.entity.Signing;
import es.soincon.proyecto.repository.IPlayerRepository;
import es.soincon.proyecto.repository.ISigningRepository;
import es.soincon.proyecto.service.IPlayerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements IPlayerService {

	private final IPlayerRepository playerRepository;
	private final ISigningRepository signingRepository;
	String routeCSV = "proyecto/src/main/resources/CSV/Players.csv";

	@Override
	@Transactional
	public List<PlayerDto> getPlayers() {
		return Optional.ofNullable(playerRepository.findAll().stream().map(PlayerAssembler::buildDtoFromEntity)
				.collect(Collectors.toList())).orElse(Collections.emptyList());
	}

	@Transactional
	@Override
	public List<PlayerDto> createPlayer(List<PlayerDto> playerDto) {

		List<PlayerDto> dtoList = playerDto;
		List<PlayerDto> auxDtoList = new ArrayList<>();

		for (int i = 0; i < dtoList.size(); i++) {

			Player player = PlayerAssembler.buildEntityFromDto(dtoList.get(i));
			playerRepository.save(player);
			auxDtoList.add(PlayerAssembler.buildDtoFromEntity(player));
		}

		return auxDtoList;
	}

	@Override
	@Transactional
	public PlayerDto getAtribute(Long id) {
		Player player = playerRepository.findById(id).orElse(null);

		if (player == null) {

			throw new EntityNotFoundException("Player");
		}
		return PlayerAssembler.buildDtoFromEntity(player);
	}

	@Override
	@Transactional
	public void deletePlayer(Long id) {

		Player player = playerRepository.findById(id).orElse(null);

		if (player == null) {

			throw new EntityNotFoundException("Player");
		}
		//Para borrar el player debo eliminar el contrato primeramente ya que violaria la integridad referencial
		//si borras los players
		//buscas en la lista que almacena el get.id del player
		
		List<Signing> signs = signingRepository.findByPlayerId(player.getId());
		//recorre en busca de esa id en el repositorio de signings y lo borra
		signs.stream().forEach(s -> signingRepository.delete(s));
		//termina borrando al jugador
		playerRepository.deleteById(id);

	}

	@Override
	@Transactional
	public PlayerDto updatePlayer(Long id, PlayerDto playerDto) {

		Assert.notNull(id, "id no puede ser null");
		Assert.notNull(playerDto, "playerDto id no puede ser null");

		Player player = playerRepository.findById(playerDto.getId()).orElse(null);

		if (player == null) {

			throw new EntityNotFoundException("Player");
		}

		player = playerRepository
				.saveAndFlush(PlayerAssembler.buildEntityFromDto(playerDto));

		return PlayerAssembler.buildDtoFromEntity(player);

	}

	@Override
	@Transactional
	public List<PlayerDto> findByName(String firstname) {

		Assert.notNull(firstname, "firstname no debe ser null");
		
		List<Player> players = playerRepository.findByFirstname(firstname);
		
		// Expresiones Lambda y streams
		return players.stream()
				.map(PlayerAssembler::buildDtoFromEntity)
				.collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	//Servicio para encontrar una lista de jugadores con Dto Minimo
	public List<PlayerMinDto> findMinDto(Long id,String firstname) {

		Assert.notNull(firstname, "firstname no debe ser null");
		
		List<Player> playerMin = playerRepository
				.findByFirstname(firstname);
		
		return playerMin.stream()
				.map(PlayerAssembler::buildMinDtoFromEntity)
				.collect(Collectors.toList());
	
	}
}
