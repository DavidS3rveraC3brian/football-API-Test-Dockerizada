package es.soincon.proyecto.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.soincon.proyecto.controller.TeamController;
import es.soincon.proyecto.dtos.PlayerDto;
import es.soincon.proyecto.dtos.TeamDto;
import es.soincon.proyecto.entity.Signing;
import es.soincon.proyecto.entity.Team;
import es.soincon.proyecto.repository.ISigningRepository;
import es.soincon.proyecto.repository.ITeamRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TeamControllerTest {

	List<Signing> signingList = new ArrayList<>();
	
	Signing signing1 = new Signing();
	
	List<Team> teamList = new ArrayList<>();

	List<TeamDto> teamDtoList = new ArrayList<>();

	Team team1 = new Team();
	Team team2 = new Team();

	@JsonProperty
	TeamDto teamDto = new TeamDto();

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private ITeamRepository teamRepository;
	
	@MockBean
	private ISigningRepository signingRepository;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private TeamController teamController;

	//Metodo para añadir un Team al repositorio ficticio.
	private void setupMockTeamRepository() {
		// Cada vez que vaya a ejecutar un test, setea valores de team y los añade a
		// la lista
		team1.setId(1L);
		team1.setName("Dortmund");
		
		team2.setId(2L);
		team2.setName("Real Madrid");
		
		teamList.add(team1);
		teamList.add(team2);
		
		teamDto.setId(3L);
		teamDto.setName("FC.Barcelona");
		teamDtoList.add(teamDto);
		
		signing1.setId(1L);
		signing1.setTeam(team1);
		signingList.add(signing1);
		
		//Controlamos el funcionamiento de los metodos que utiliza el Repositorio
		when(teamRepository.findAll()).thenReturn(teamList);

		when(teamRepository.saveAndFlush(Mockito.any(Team.class))).thenAnswer((Answer<Team>) invocation -> {
			Team teamToSave = invocation.getArgument(0);
			teamList.add(teamToSave);

			return teamToSave;

		});
		
		when(teamRepository.findById(Mockito.anyLong())).thenAnswer((Answer<Optional<Team>>) invocation -> {
			Long idToFind = invocation.getArgument(0);
			Optional<Team> team = teamList.stream()
					.filter(t -> t.getId().equals(idToFind))
					.findFirst();
			return team;
		});
		
		doAnswer(invocation -> {
		    Long idTeamToDelete = invocation.getArgument(0);
		    Long idSigningToDelete = invocation.getArgument(0);
		    
		    signingList.removeIf(signing -> signing.getId().equals(idSigningToDelete));
		    teamList.removeIf(team -> team.getId().equals(idTeamToDelete));
		    
		    return null; // El método `deleteById` no devuelve un valor, por lo que se retorna null.
		}).when(teamRepository).deleteById(Mockito.anyLong());


		

	}
	
	@BeforeEach
	@Order(1)
	public void setup() {
		// Configurar datos en el repositorio para todas las pruebas
		setupMockTeamRepository();

	}
	
	@Test
	@Order(2)
	public void testGetAllTeams() throws Exception {
		
		// Realizamos la peticion
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/team").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		
		// Verificamos los datos obtenidos
		String content = result.getResponse().getContentAsString();
		
		// Convertimos el contenido Json en una lista para verificar valores
				List<Team> resultadosController = objectMapper.readValue(content, new TypeReference<List<Team>>() {
				});
				
				// Verificamos que no esta vacia la lista
				assertTrue(!resultadosController.isEmpty());

				// Accedemos a los valores y hacemos asserts
				Team team1 = resultadosController.get(0);
				assertEquals(1L, team1.getId());
				assertEquals("Dortmund", team1.getName());

				Team team2 = resultadosController.get(1);
				assertEquals(2L, team2.getId());
				assertEquals("Real Madrid", team2.getName());
	}
	
	@Test
	@Order(3)
	public void testGetTeamsById() throws Exception {
		
		Long expectedValue = 1L;
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/team/{id}", expectedValue).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedValue))
				.andReturn();
		
		String responseContent = result.getResponse().getContentAsString();
		TeamDto teamId = objectMapper.readValue(responseContent, new TypeReference<TeamDto>() {
		});
		
		assertThat(teamId).extracting(TeamDto::getId).contains(expectedValue);
	}
	
	@Test
	@Order(4)
	public void testCreateBatchTeam() throws Exception {
		
		Long expectedValue = 3L;
		String teamDtoJson = objectMapper.writeValueAsString(teamDtoList);
		
		// Realiza una solicitud POST para crear el jugador
				MvcResult result = mockMvc.perform(
						MockMvcRequestBuilders.post("/team")
						.contentType(MediaType.APPLICATION_JSON)
						.content(teamDtoJson))
						.andExpect(MockMvcResultMatchers.status().isCreated())
						.andReturn();

				String responseContent = result.getResponse().getContentAsString();

				List<TeamDto> teamId = objectMapper.readValue(responseContent, new TypeReference<List<TeamDto>>() {
					});
					
					assertThat(teamId).extracting(TeamDto::getId).contains(expectedValue);
					
	}
	
	@Test
	@Order(5)
	public void testDeleteTeam() throws Exception {

		Long expectedValue = 1L;

		MvcResult getResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/team/{id}", expectedValue)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedValue))
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		// Obtengo el jugador de la respuesta JSON
		String teamJson = getResult.getResponse().getContentAsString();
		team1 = objectMapper.readValue(teamJson, Team.class);
		
		Long idTeam1Long = team1.getId();

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/team/{id}", idTeam1Long)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		Mockito.verify(teamRepository).deleteById(idTeam1Long);
		
		assertFalse(teamList.contains(team1));

	}
	
	@Test
	@Order(7)
	public void testUpdateTeam() throws Exception {

		// Creamos el Dto y seteamos los valores
		TeamDto teamDtoToUpdate = new TeamDto();
		teamDtoToUpdate.setId(2L);
		teamDtoToUpdate.setName("nombreActualizado");

		// Convertimos el objeto DTO a JSON
		String teamDtoJson = objectMapper.writeValueAsString(teamDtoToUpdate);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put("/team/{id}", 2L).contentType(MediaType.APPLICATION_JSON)
						.content(teamDtoJson))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		// Verificamos la respuesta que contiene el jugador actualizado
		String responseContent = result.getResponse().getContentAsString();
		TeamDto updatedTeamDto = objectMapper.readValue(responseContent, TeamDto.class);

		// Realizamos las aserciones en el jugador actualizado
		assertEquals(teamDtoToUpdate.getId(), updatedTeamDto.getId());
		assertEquals(teamDtoToUpdate.getName(), updatedTeamDto.getName());

	}
}
