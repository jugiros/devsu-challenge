package com.challenge.ClientePersona.repositories;
import com.challenge.ClientePersona.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    boolean existsByIdentificacion(String identificacion);

}