package com.armando.personas.ServiceImpl;

import com.armando.personas.Models.Persona;
import com.armando.personas.Repository.PersonaRepository;
import com.armando.personas.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Persona> findAll() {
        return (List<Persona>) personaRepository.findAll();
    }

    @Override
    public Optional<Persona> findById(Long Id) {
        return personaRepository.findById(Id);
    }

    @Override
    public List<Persona> buscarpornombre(String nombre) {
        return personaRepository.findporNombre(nombre);
    }

    @Override
    public Optional<Persona> findByNombreEmail(String nombre, String apellidoP, String apellidoM, String Email) {
        return personaRepository.findByNombreAndAPaternoAndAMaternoOrEmail(nombre, apellidoP, apellidoM, Email);
    }

    @Override
    public Persona savePersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public Long deletePersonayId(Long Id) {
        Optional<Persona> persona = personaRepository.findById(Id);
        personaRepository.deleteById(Id);
        return persona.get().getIdPersona();
    }
}
