package es.soincon.proyecto.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
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

import es.soincon.proyecto.controller.PlayerController;
import es.soincon.proyecto.dtos.PlayerDto;
import es.soincon.proyecto.dtos.TeamDto;
import es.soincon.proyecto.entity.Player;
import es.soincon.proyecto.entity.Team;
import es.soincon.proyecto.repository.IPlayerRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayerControllerTest {

	List<Player> playerList = new ArrayList<>();

	List<PlayerDto> playersDto = new ArrayList<>();

	Player player1 = new Player();
	Player player2 = new Player();

	@JsonProperty
	PlayerDto playerDto = new PlayerDto();

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private IPlayerRepository playerRepository;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private PlayerController playerController;

	// Metodo para añadir un Player al repositorio ficticio.
	private void setupMockPlayerRepository() {
		// Cada vez que vaya a ejecutar un test, setea valores de player y los añade a
		// la lista
		player1.setId(1L);
		player1.setFirstname("david");

		player2.setId(2L);
		player2.setFirstname("pepe");

		playerList.add(player1);
		playerList.add(player2);

		playerDto.setId(2L);
		playerDto.setFirstname("pepe");
		playerDto.setLastname("contreras");
		playersDto.add(playerDto);

		// Controlamos el funcionamiento del metodo FindAll del repositorio simulado
		when(playerRepository.findAll()).thenReturn(playerList);
		// Controlamos el funcionamiento del metodo findById del repositorio simulado
		when(playerRepository.findById(1L)).thenReturn(Optional.of(player1));
		// Controlamos el funcionamiento del metodo findByFirstname del repositorio
		// simulado
		when(playerRepository.findByFirstname(Mockito.anyString())).thenAnswer((Answer<List<Player>>) invocation -> {
			String firstname = invocation.getArgument(0);
			return playerList.stream().filter(p -> p.getFirstname().equals(firstname)).collect(Collectors.toList());
		});
		// Controlamos que el funcionamiento del metodo saveAndFlush del repositorio
		// simulado
		when(playerRepository.saveAndFlush(Mockito.any(Player.class))).thenAnswer((Answer<Player>) invocation -> {
			Player playerToSave = invocation.getArgument(0);
			playerList.add(playerToSave);

			return playerToSave;

		});
		//Control de delete
		doAnswer(invocation -> {
		    Long idPlayerToDelete = invocation.getArgument(0);
 
		    playerList.removeIf(player -> player.getId().equals(idPlayerToDelete));

		    
		    return null; // El método `deleteById` no devuelve un valor, por lo que se retorna null.
		}).when(playerRepository).deleteById(Mockito.anyLong());


	}

	@BeforeEach
	@Order(1)
	public void setup() {
		// Configurar datos en el repositorio para todas las pruebas
		setupMockPlayerRepository();

	}

	@Test
	@Order(2)
	public void testGetAllPlayers() throws Exception {

		// Realizamos la peticion
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/player").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andReturn();

		// Verificamos los datos obtenidos
		String content = result.getResponse().getContentAsString();

		// Convertimos el contenido Json en una lista para verificar valores
		List<Player> resultadosController = objectMapper.readValue(content, new TypeReference<List<Player>>() {
		});

		// Verificamos que no esta vacia la lista
		assertTrue(!resultadosController.isEmpty());

		// Accedemos a los valores y hacemos asserts
		Player player1 = resultadosController.get(0);
		assertEquals(1L, player1.getId());
		assertEquals("david", player1.getFirstname());

		Player player2 = resultadosController.get(1);
		assertEquals(2L, player2.getId());
		assertEquals("pepe", player2.getFirstname());

	}

	@Test
	@Order(3)
	public void testGetPlayerById() throws Exception {

		Long expectedValue = 1L;

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/player/{id}", expectedValue).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedValue))
				.andReturn();
		
		String responseContent = result.getResponse().getContentAsString();
		PlayerDto playerId = objectMapper.readValue(responseContent, new TypeReference<PlayerDto>() {
		});
		
		assertThat(playerId).extracting(PlayerDto::getId).contains(expectedValue);

	}

	@Test
	@Order(4)
	public void testGetPlayersByName() throws Exception {

		String expectedValue = "david";

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/player/search-by-name/{name}", expectedValue)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

		String responseContent = result.getResponse().getContentAsString();
		List<PlayerDto> players = objectMapper.readValue(responseContent, new TypeReference<List<PlayerDto>>() {
		});

		boolean found = players.stream().anyMatch(player -> player.getFirstname().equals(expectedValue));

		assertTrue(found);
	}

	@Test
	@Order(5)
	public void testCreateBatchPlayer() throws Exception {

		// Convierte la lista de jugadores DTO en JSON
		String playerDtoJson = objectMapper.writeValueAsString(playersDto);

		// Realiza una solicitud POST para crear el jugador
		mockMvc.perform(
				MockMvcRequestBuilders.post("/player").contentType(MediaType.APPLICATION_JSON).content(playerDtoJson))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		String expectedValue = "pepe";

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/player/search-by-name/{name}", expectedValue)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

		String responseContent = result.getResponse().getContentAsString();
		List<PlayerDto> players = objectMapper.readValue(responseContent, new TypeReference<List<PlayerDto>>() {
		});

		// Verifica que la respuesta contenga un jugador con el nombre esperado
		assertThat(players).extracting(PlayerDto::getFirstname).contains(expectedValue);

	}

	@Test
	@Order(6)
	public void testDeletePlayer() throws Exception {

			Long expectedValue = 1L;

			MvcResult getResult = mockMvc
					.perform(MockMvcRequestBuilders.get("/player/{id}", expectedValue)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
					.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedValue))
					.andDo(MockMvcResultHandlers.print())
					.andReturn();

			// Obtengo el jugador de la respuesta JSON
			String playerJson = getResult.getResponse().getContentAsString();
			player1 = objectMapper.readValue(playerJson, Player.class);
			
			Long idPlayer1Long = player1.getId();

			mockMvc.perform(
					MockMvcRequestBuilders.delete("/player/{id}", idPlayer1Long)
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isAccepted())
					.andDo(MockMvcResultHandlers.print())
					.andReturn();
			
			Mockito.verify(playerRepository).deleteById(idPlayer1Long);

			assertFalse(playerList.contains(player1));


	}

	@Test
	@Order(7)
	public void testUpdatePlayer() throws Exception {

		// Creamos el Dto y seteamos los valores
		PlayerDto playerDtoToUpdate = new PlayerDto();
		playerDtoToUpdate.setId(1L);
		playerDtoToUpdate.setFirstname("nombreActualizado");

		// Convertimos el objeto DTO a JSON
		String playerDtoJson = objectMapper.writeValueAsString(playerDtoToUpdate);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put("/player/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
						.content(playerDtoJson))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andReturn();

		// Verificamos la respuesta que contiene el jugador actualizado
		String responseContent = result.getResponse().getContentAsString();
		PlayerDto updatedPlayerDto = objectMapper.readValue(responseContent, PlayerDto.class);

		// Realizamos las aserciones en el jugador actualizado
		assertEquals(playerDtoToUpdate.getId(), updatedPlayerDto.getId());
		assertEquals(playerDtoToUpdate.getFirstname(), updatedPlayerDto.getFirstname());

	}

}
