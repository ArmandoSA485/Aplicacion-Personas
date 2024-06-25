package com.armando.personas.Service;

import com.armando.personas.Models.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaService {

    List<Persona> findAll ();

    Optional<Persona> findById (Long Id);

    List<Persona> buscarpornombre (String nombre);

    public Optional<Persona> findByNombreEmail(String nombre, String apellidoP, String apellidoM, String Email);

    Persona savePersona (Persona persona);

    Long deletePersonayId (Long Id);
}
