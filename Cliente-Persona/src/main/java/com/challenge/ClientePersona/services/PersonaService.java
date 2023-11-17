package com.challenge.ClientePersona.services;
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

    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    public Optional<Persona> getPersonaById(Long id) {
        return personaRepository.findById(id);
    }

    public Persona createOrUpdatePersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public void deletePersonaById(Long id) {
        personaRepository.deleteById(id);
    }
}
