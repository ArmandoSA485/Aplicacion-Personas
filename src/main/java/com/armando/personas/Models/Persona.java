package com.armando.personas.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table (name = "persona")
public class Persona {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdPersona;

    @Column(unique = false)
    private String nombre;

    @Column(length = 50)
    private String aPaterno;

    @Column
    private String aMaterno;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "ddMMyyyy")
    @Column
    private LocalDate fecNacimiento;

    @Column
    private String direccion;

    @Column
    @NotNull
    @NotBlank
    @Pattern(regexp = "[0-9]*", message = "Número de teléfono no válido")
    @Size(min = 10,max = 10,message = "Ingresar Numero a 10 digitos")
    private String telefono;

    @Transient
    private Integer Edad;

    @Column
    @NotEmpty
    @Email(message = "El correo Electronico debe tener el formato user@ejemplo.com")
    private String email;

    @OneToMany(mappedBy = "persona")
    private List<Hijo> Hijos;


    public String getaMaterno() {
        return aMaterno;
    }

    public void setaMaterno(String aMaterno) {
        this.aMaterno = aMaterno;
    }

    public String getaPaterno() {
        return aPaterno;
    }

    public void setaPaterno(String aPaterno) {
        this.aPaterno = aPaterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public @NotEmpty @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty @Email String email) {
        this.email = email;
    }

    public LocalDate getFecNacimiento() {
        return fecNacimiento;
    }

    public void setFecNacimiento(@NotNull @NotBlank LocalDate fecNacimiento) {
        this.fecNacimiento = fecNacimiento;
    }

    public Long getIdPersona() {
        return IdPersona;
    }

    public void setIdPersona(Long idPersona) {
        IdPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public @NotNull @NotBlank @Pattern(regexp = "\\+?[0-9()-]*", message = "Número de teléfono no válido") @Size(min = 10, max = 10, message = "Ingresar Numero a 10 digitos") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotNull @NotBlank @Pattern(regexp = "\\+?[0-9()-]*", message = "Número de teléfono no válido") @Size(min = 10, max = 10, message = "Ingresar Numero a 10 digitos") String telefono) {
        this.telefono = telefono;
    }

    public Integer getEdad() {
        return Edad;
    }

    public void setEdad(Integer edad) {
        Edad = edad;
    }

    public List<Hijo> getHijos() {
        return Hijos;
    }

    public void setHijos(List<Hijo> hijos) {
        Hijos = hijos;
    }

    public Persona(Long idPersona, String nombre, String aPaterno, String aMaterno, LocalDate fecNacimiento, String direccion, String telefono, Integer edad, String email) {
        IdPersona = idPersona;
        this.nombre = nombre;
        this.aPaterno = aPaterno;
        this.aMaterno = aMaterno;
        this.fecNacimiento = fecNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        Edad = edad;
        this.email = email;
    }

    public Persona() {
        super();
    }
}
