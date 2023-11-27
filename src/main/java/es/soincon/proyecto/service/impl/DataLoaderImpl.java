package es.soincon.proyecto.service.impl;

import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVReader;

import es.soincon.proyecto.assemblers.PlayerAssembler;
import es.soincon.proyecto.assemblers.SigningAssembler;
import es.soincon.proyecto.assemblers.TeamAssembler;
import es.soincon.proyecto.dtos.PlayerDto;
import es.soincon.proyecto.dtos.SigningDto;
import es.soincon.proyecto.dtos.TeamDto;
import es.soincon.proyecto.entity.Player;
import es.soincon.proyecto.entity.Signing;
import es.soincon.proyecto.entity.Team;
import es.soincon.proyecto.repository.IPlayerRepository;
import es.soincon.proyecto.repository.ISigningRepository;
import es.soincon.proyecto.repository.ITeamRepository;
import es.soincon.proyecto.service.IDataLoader;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/environment.properties")
public class DataLoaderImpl implements IDataLoader {

	private @Value("${players.csv}") String playersCsv;
	private @Value("${teams.csv}") String teamsCsv;
	private @Value("${signings.csv}") String signingsCsv;

	private final IPlayerRepository playerRepository;
	private final ITeamRepository teamRepository;
	private final ISigningRepository signingRepository;

	
//	private @Value("${data.load.enabled}") boolean dataLoadEnabled;
	
//    @PostConstruct
//    public void loadDataOnStartup() {
//        loadPlayers();
//        loadTeams();
//        loadSignings();
//    }
    
	@Transactional
	public List<PlayerDto> loadPlayers() {

		List<PlayerDto> playerDtos = new ArrayList<>();

		try (CSVReader csvReader = new CSVReader(new FileReader(playersCsv))) {

			// Leer la primera línea que contiene los nombres de las columnas
			String[] encabezados = csvReader.readNext();

			if (encabezados != null) {

				String[] linea;
				while ((linea = csvReader.readNext()) != null) {
					PlayerDto playerDto = new PlayerDto();

					// Asignar los datos a la instancia de Player
					playerDto.setId(Long.parseLong(linea[0]));
					playerDto.setFirstname(linea[1]);
					playerDto.setLastname(linea[2]);
					playerDto.setEmail(linea[3]);
					playerDto.setBirthdate(LocalDate.parse(linea[4]));
					playerDto.setPosition(linea[5]);
					playerDto.setGender(linea[6]);
					playerDto.setWeight(Long.parseLong(linea[7]));
					playerDto.setHigh(Long.parseLong(linea[8]));
					playerDto.setImc(new BigDecimal(linea[9]));
					playerDto.setFat(Long.parseLong(linea[10]));

					playerDtos.add(playerDto);
				}
			}

		} catch (Exception e) {

			// Manejar posibles excepciones
			e.printStackTrace();
		}

		for (PlayerDto playerDto : playerDtos) {

			Player player = PlayerAssembler.buildEntityFromDto(playerDto);

			playerRepository.save(player);
		}

		return playerDtos;
	}

	@Transactional
	public List<TeamDto> loadTeams() {

		List<TeamDto> teamDtos = new ArrayList<>();

		try (CSVReader csvReader = new CSVReader(new FileReader(teamsCsv))) {

			// Leer la primera línea que contiene los nombres de las columnas
			String[] encabezados = csvReader.readNext();

			if (encabezados != null) {

				String[] linea;
				while ((linea = csvReader.readNext()) != null) {
					TeamDto teamDto = new TeamDto();

					// Asignar los datos a la instancia de team
					teamDto.setId(Long.parseLong(linea[0]));
					teamDto.setName(linea[1]);
					teamDto.setEmail(linea[2]);
					teamDto.setSince(LocalDate.parse(linea[3]));
					teamDto.setCity(linea[4]);

					teamDtos.add(teamDto);
				}
			}

		} catch (Exception e) {

			// Manejar posibles excepciones
			e.printStackTrace();
		}

		for (TeamDto teamDto : teamDtos) {

			Team team = TeamAssembler.buildEntityFromDto(teamDto);

			teamRepository.save(team);
		}

		return teamDtos;
	}
	
	@Transactional
	public List<SigningDto> loadSignings() {

		List<SigningDto> signingDtos = new ArrayList<>();

		try (CSVReader csvReader = new CSVReader(new FileReader(signingsCsv))) {

			// Leer la primera línea que contiene los nombres de las columnas
			String[] encabezados = csvReader.readNext();

			if (encabezados != null) {

				String[] linea;
				while ((linea = csvReader.readNext()) != null) {
					SigningDto signingDto = new SigningDto();

					// Asignar los datos a la instancia de Signing
					signingDto.setId(Long.parseLong(linea[0]));
					signingDto.setPlayerId(Long.parseLong(linea[1]));
					signingDto.setTeamId(Long.parseLong(linea[2]));
					signingDto.setSince(LocalDate.parse(linea[3]));
					signingDto.setUntil(LocalDate.parse(linea[4]));

					signingDtos.add(signingDto);
				}
			}

		} catch (Exception e) {

			// Manejar posibles excepciones
			e.printStackTrace();
		}

		for (SigningDto signingDto : signingDtos) {
			Player player = playerRepository.findById(signingDto.getPlayerId()).get();
			Team team = teamRepository.findById(signingDto.getTeamId()).get();
			Signing signing = SigningAssembler.buildEntityFromDto(signingDto, player, team);

			signingRepository.save(signing);
		}

		return signingDtos;
	}
}