package com.armando.personas.Repository;

import com.armando.personas.Models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona,Long> {

    Optional<Persona> findByNombreAndAPaternoAndAMaternoOrEmail (String nombre, String apellidoP, String apellidoM, String Email);


    @Query("select u from Persona u where u.nombre LIKE %?1%")
    List<Persona> findporNombre(String nombre);

}
