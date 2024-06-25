package com.armando.personas.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "hijo")
public class Hijo {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long idHijo;

    @Column
    private String Nombre;

    @Column
    private Integer Edad;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idPersonaPadre")
    private Persona persona;

    public Long getIdHijo() {
        return idHijo;
    }

    public void setIdHijo(Long idHijo) {
        this.idHijo = idHijo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Integer getEdad() {
        return Edad;
    }

    public void setEdad(Integer edad) {
        Edad = edad;
    }

    public Hijo(Long idHijo, Integer edad) {
        this.idHijo = idHijo;
        Edad = edad;
    }

    public Hijo() {
        super();
    }

    public Hijo(String nombre, Integer edad) {
        Nombre = nombre;
        Edad = edad;
    }
}
