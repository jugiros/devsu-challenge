package com.challenge.ClientePersona.services;
import com.challenge.ClientePersona.dto.PersonaClienteDTO;
import com.challenge.ClientePersona.exceptions.ClienteNotFoundException;
import com.challenge.ClientePersona.exceptions.IdentificacionUnicaException;
import com.challenge.ClientePersona.models.Cliente;
import com.challenge.ClientePersona.models.Persona;
import com.challenge.ClientePersona.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final PersonaService personaService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PersonaService personaService) {
        this.clienteRepository = clienteRepository;
        this.personaService = personaService;
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente createOrUpdateCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente createClienteFromDTO(PersonaClienteDTO personaClienteDTO) {
        String identificacion = personaClienteDTO.getIdentificacion();
        if (personaService.existsByIdentificacion(identificacion)) {
            throw new IdentificacionUnicaException("La identificación '" + identificacion + "' ya está en uso");
        }

        Persona persona = new Persona();
        persona = personaService.createOrUpdateFromDTO(persona, personaClienteDTO); // Corregir aquí

        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        cliente.setContraseña(personaClienteDTO.getContraseña());
        cliente.setEstado(personaClienteDTO.getEstado());

        return createOrUpdateCliente(cliente);
    }

    @Transactional
    public Cliente updatePersonaAndClienteFromClienteId(Long clienteId, PersonaClienteDTO personaClienteDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId));

        Persona persona = cliente.getPersona();
        String nuevaIdentificacion = personaClienteDTO.getIdentificacion();

        if (!persona.getIdentificacion().equals(nuevaIdentificacion) && personaService.existsByIdentificacion(nuevaIdentificacion)) {
            throw new IdentificacionUnicaException("La identificación '" + nuevaIdentificacion + "' ya está en uso");
        }

        personaService.updateFromDTO(persona, personaClienteDTO);

        cliente.setContraseña(personaClienteDTO.getContraseña());
        cliente.setEstado(personaClienteDTO.getEstado());

        return createOrUpdateCliente(cliente);
    }

    @Transactional
    public void deletePersonaAndClienteByClienteId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId));

        Persona persona = cliente.getPersona();
        clienteRepository.delete(cliente);

        if (persona != null) {
            personaService.deletePersonaById(persona.getId());
        }
    }
}