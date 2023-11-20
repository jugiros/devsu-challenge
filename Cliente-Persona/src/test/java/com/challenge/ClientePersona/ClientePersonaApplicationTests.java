package com.challenge.ClientePersona;

import com.challenge.ClientePersona.controllers.ClienteController;
import com.challenge.ClientePersona.dto.PersonaClienteDTO;
import com.challenge.ClientePersona.models.Cliente;
import com.challenge.ClientePersona.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientePersonaApplicationTests {

	@Mock
	private ClienteService clienteService;

	@InjectMocks
	private ClienteController clienteController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateClienteFromDTO() {
		PersonaClienteDTO personaClienteDTO = new PersonaClienteDTO();

		Cliente clienteMock = new Cliente();
		when(clienteService.createClienteFromDTO(any(PersonaClienteDTO.class))).thenReturn(clienteMock);

		ResponseEntity<Cliente> response = clienteController.createClienteFromDTO(personaClienteDTO);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testUpdatePersonaAndClienteFromClienteId() {
		Long id = 1L;
		PersonaClienteDTO personaClienteDTO = new PersonaClienteDTO();

		Cliente clienteMock = new Cliente();

		when(clienteService.updatePersonaAndClienteFromClienteId(eq(id), any(PersonaClienteDTO.class))).thenReturn(clienteMock);

		ResponseEntity<Cliente> response = clienteController.updatePersonaAndClienteFromClienteId(id, personaClienteDTO);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}