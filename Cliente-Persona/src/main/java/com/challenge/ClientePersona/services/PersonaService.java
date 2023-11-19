package com.challenge.ClientePersona.services;
import com.challenge.ClientePersona.dto.PersonaClienteDTO;
import com.challenge.ClientePersona.models.Persona;
import com.challenge.ClientePersona.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;

    @Autowired
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Persona createOrUpdatePersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public void deletePersonaById(Long id) {
        personaRepository.deleteById(id);
    }

    public boolean existsByIdentificacion(String identificacion) {
        return personaRepository.existsByIdentificacion(identificacion);
    }

    public Persona createOrUpdateFromDTO(Persona persona, PersonaClienteDTO personaClienteDTO) {
        persona.setNombre(personaClienteDTO.getNombre());
        persona.setGenero(personaClienteDTO.getGenero());
        persona.setEdad(personaClienteDTO.getEdad());
        persona.setIdentificacion(personaClienteDTO.getIdentificacion());
        persona.setDireccion(personaClienteDTO.getDireccion());
        persona.setTelefono(personaClienteDTO.getTelefono());

        return createOrUpdatePersona(persona);
    }

    public void updateFromDTO(Persona persona, PersonaClienteDTO personaClienteDTO) {
        persona.setNombre(personaClienteDTO.getNombre());
        persona.setGenero(personaClienteDTO.getGenero());
        persona.setEdad(personaClienteDTO.getEdad());
        persona.setIdentificacion(personaClienteDTO.getIdentificacion());
        persona.setDireccion(personaClienteDTO.getDireccion());
        persona.setTelefono(personaClienteDTO.getTelefono());

        createOrUpdatePersona(persona);
    }
}