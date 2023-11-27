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

import es.soincon.proyecto.assemblers.TeamAssembler;
import es.soincon.proyecto.dtos.TeamDto;
import es.soincon.proyecto.entity.Signing;
import es.soincon.proyecto.entity.Team;
import es.soincon.proyecto.repository.ISigningRepository;
import es.soincon.proyecto.repository.ITeamRepository;
import es.soincon.proyecto.service.ITeamService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {

	private final ITeamRepository teamRepository;
	private final ISigningRepository signingRepository;

	@Override
	@Transactional
	public List<TeamDto> createTeam(List<TeamDto> teamDto) {

		List<TeamDto> dtoList = teamDto;
		List<TeamDto> auxDtoList = new ArrayList<>();

		for (int i = 0; i < dtoList.size(); i++) {

			Team team = TeamAssembler.buildEntityFromDto(dtoList.get(i));
			teamRepository.save(team);
			auxDtoList.add(TeamAssembler.buildDtoFromEntity(team));
		}

		return auxDtoList;
	}

	@Override
	@Transactional
	public List<TeamDto> getTeams() {
		return Optional.ofNullable(
				teamRepository.findAll().stream().map(TeamAssembler::buildDtoFromEntity).collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

	@Override
	@Transactional
	public TeamDto getAtribute(Long id) {
		Team team = teamRepository.findById(id).orElse(null);

		if (team == null) {

			throw new EntityNotFoundException("Team");
		}
		return TeamAssembler.buildDtoFromEntity(team);
	}

	@Override
	@Transactional
	public void deleteTeam(Long id) {
		
		Team team = teamRepository.findById(id).orElse(null);

		if (team == null) {

			throw new EntityNotFoundException("Team");
		}
		//Para borrar el player debo eliminar el contrato primeramente ya que violaria la integridad referencial
		//Si quiero borrar un equipo
		//busco en la lista que almacena el get.id del equipo
		List<Signing> signs = signingRepository.findByTeamId(team.getId());
		//recorre en busca de esa id en el repositorio de signings y lo borra
		signs.stream().forEach(s -> signingRepository.delete(s));
		//termina borrando el equipo
		teamRepository.deleteById(id);

	}

	@Override
	@Transactional
	public TeamDto updateTeam(Long id, TeamDto teamDto) {
		Assert.notNull(id, "id no puede ser null");
		Assert.notNull(teamDto, "teamDto id no puede ser null");

		Team team = teamRepository.findById(id).orElse(null);

		if (team == null) {

			throw new EntityNotFoundException("Team");
		}
		team = teamRepository.saveAndFlush(TeamAssembler.buildEntityFromDto(teamDto));

		return TeamAssembler.buildDtoFromEntity(team);
	}

//	@Override
//	@Transactional
//	public TeamDto findByName(String name) {
//		
//		Assert.notNull(name, "nombre debe ser null");
//
//		Team team = (Team) teamRepository.findByName(name);
//
//		return TeamAssembler.buildDtoFromEntity(team);
//	}
	
	@Override
	@Transactional
	public List<TeamDto> findByName(String name) {
		
		Assert.notNull(name, "nombre no debe ser null");
		
		List<Team> teams = teamRepository.findByName(name);
		
		return teams.stream()
				.map(TeamAssembler::buildDtoFromEntity)
				.collect(Collectors.toList());
	}

}
