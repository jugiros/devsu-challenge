package com.challenge.ClientePersona.services;
import com.challenge.ClientePersona.dto.PersonaClienteDTO;
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

    public void deleteClienteById(Long id) {
        clienteRepository.deleteById(id);
    }

    @Transactional
    public Cliente createClienteFromDTO(PersonaClienteDTO personaClienteDTO) {
        // Crear la persona desde el DTO
        Persona persona = new Persona();
        persona.setNombre(personaClienteDTO.getNombre());
        persona.setGenero(personaClienteDTO.getGenero());
        persona.setEdad(personaClienteDTO.getEdad());
        persona.setIdentificacion(personaClienteDTO.getIdentificacion());
        persona.setDireccion(personaClienteDTO.getDireccion());
        persona.setTelefono(personaClienteDTO.getTelefono());

        // Guardar la persona y obtenerla con el ID asignado
        persona = personaService.createOrUpdatePersona(persona);

        // Crear el cliente asociado a la persona
        Cliente cliente = new Cliente();
        cliente.setPersona(persona); // Asignar la persona creada al cliente
        cliente.setContraseña(personaClienteDTO.getContraseña());
        cliente.setEstado(personaClienteDTO.getEstado());

        // Guardar el cliente
        return createOrUpdateCliente(cliente);
    }
}
