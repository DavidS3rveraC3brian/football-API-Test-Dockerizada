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

import es.soincon.proyecto.assemblers.SigningAssembler;
import es.soincon.proyecto.dtos.SigningDto;
import es.soincon.proyecto.entity.Player;
import es.soincon.proyecto.entity.Signing;
import es.soincon.proyecto.entity.Team;
import es.soincon.proyecto.repository.IPlayerRepository;
import es.soincon.proyecto.repository.ISigningRepository;
import es.soincon.proyecto.repository.ITeamRepository;
import es.soincon.proyecto.service.ISigningService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SigningServiceImpl implements ISigningService {

	private final ISigningRepository signingRepository;
	private final IPlayerRepository playerRepository;
	private final ITeamRepository teamRepository;

	@Override
	@Transactional
	public List<SigningDto> createSigning(List<SigningDto> signingDto) {

		List<SigningDto> dtoList = signingDto;
		List<SigningDto> auxDtoList = new ArrayList<>();

		for (int i = 0; i < dtoList.size(); i++) {

			Player player = new Player();
			SigningDto x = dtoList.get(i);
			if (x.getPlayerId() == null) {

				player = null;

			} else {

				player = playerRepository.findById(x.getPlayerId()).orElse(null);

				if (player == null) {

					throw new EntityNotFoundException("Signing");
				}
			}
			Team team = teamRepository.findById(x.getTeamId()).orElse(null);

			if (team == null) {

				throw new EntityNotFoundException("Team");
			}

			Signing signing = SigningAssembler.buildEntityFromDto(x, player, team);
			signing.setTeam(team);
			signing.setPlayer(player);

			signingRepository.save(signing);

			auxDtoList.add(SigningAssembler.buildDtoFromEntity(signing));

		}
		return auxDtoList;

	}

	@Override
	@Transactional
	public List<SigningDto> getSignings() {
		return Optional.ofNullable(signingRepository.findAll().stream().map(SigningAssembler::buildDtoFromEntity)
				.collect(Collectors.toList())).orElse(Collections.emptyList());
	}

	@Override
	@Transactional
	public SigningDto getSigning(Long id) {

		Signing signing = signingRepository.findById(id).orElse(null);

		if (signing == null) {

			throw new EntityNotFoundException("Signing");
		}
		return SigningAssembler.buildDtoFromEntity(signing);
	}

	@Override
	@Transactional
	public void deleteSigning(Long id) {

		Signing signing = signingRepository.findById(id).orElse(null);

		if (signing == null) {

			throw new EntityNotFoundException("Signing");
		}
		signingRepository.deleteById(id);

	}

	@Override
	@Transactional
	public SigningDto updateSigning(Long id, SigningDto signingDto) {

		Assert.notNull(id, "id no puede ser null");
		Assert.notNull(signingDto, "playerDto id no puede ser null");

		Player player = new Player();

		if (signingDto.getPlayerId() == null) {

			player = null;

		} else {

			player = playerRepository.findById(signingDto.getPlayerId()).orElse(null);

			if (player == null) {

				throw new EntityNotFoundException("Signing");

			}
		}

		Team team = teamRepository.findById(signingDto.getTeamId()).orElse(null);

		if (team == null) {

			throw new EntityNotFoundException("Team");
		}

		Signing signing = signingRepository.findById(signingDto.getTeamId()).orElse(null);

		if (signing == null) {

			throw new EntityNotFoundException("Signing");
		}

		signing = signingRepository.saveAndFlush(SigningAssembler.buildEntityFromDto(signingDto, player, team));

		return SigningAssembler.buildDtoFromEntity(signing);
	}

	@Transactional
	public SigningDto findPlayerFromId(Long playerid) {

		Assert.notNull(playerid, "playerId no puede ser null");

		List<Signing> signing = signingRepository.findByPlayerId(playerid);

		List<SigningDto> signingDtos = new ArrayList<SigningDto>();

		for (Signing player : signing) {

			signingDtos.add(SigningAssembler.buildDtoFromEntity(player));
		}

		return (SigningDto) signingDtos;

	}

	@Override
	@Transactional
	public SigningDto findById(Long id) {
		Assert.notNull(id, "id no debe ser null");
		Signing signing = signingRepository.findById(id).get();
		return SigningAssembler.buildDtoFromEntity(signing);

	}

}
