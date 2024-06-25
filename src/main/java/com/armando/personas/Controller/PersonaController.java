package com.armando.personas.Controller;

import com.armando.personas.DTOS.ResponseNotFoundDTO;
import com.armando.personas.Models.Persona;
import com.armando.personas.Service.HijoService;
import com.armando.personas.Service.PersonaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@RestController
@RequestMapping("/personas")
public class PersonaController {

    private static final Logger log = LoggerFactory.getLogger(PersonaController.class);
    @Autowired
    private PersonaService personaService;

    @Autowired
    private HijoService hijoService;

    @GetMapping("/")
    public ResponseEntity<?> findAll (){
        log.info("Inicia busqueda de todas las personas.");
        List<Persona> personaList = personaService.findAll();
        if(personaList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNotFoundDTO(400,"No se encontraron personas."));
        }
        personaList.forEach(persona -> persona.setEdad(Period.between(persona.getFecNacimiento(),LocalDate.now()).getYears()));
        return ResponseEntity.status(HttpStatus.OK).body(personaList);    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> findById (@PathVariable Long Id){
        try{
            Optional<Persona> personaOptional = personaService.findById(Id);
            if (personaOptional.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNotFoundDTO(400,"No se encontró la persona."));
            }else {
                personaOptional.get().setEdad(Period.between(personaOptional.get().getFecNacimiento(),LocalDate.now()).getYears());
                return ResponseEntity.status(HttpStatus.OK).body(personaOptional);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseNotFoundDTO(999,e.getMessage()));
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> findbyNombre (@PathVariable String nombre){
            List<Persona> personaList = personaService.buscarpornombre(nombre);
            if(personaList.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNotFoundDTO(400,"No se encontraron personas."));
            }
            personaList.forEach(persona -> persona.setEdad(Period.between(persona.getFecNacimiento(),LocalDate.now()).getYears()));
            return ResponseEntity.status(HttpStatus.OK).body(personaList);
    }

    @PostMapping("/")
    public ResponseEntity<?> savePersona (@Valid @RequestBody Persona persona, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional <Persona> personaO = personaService.findByNombreEmail(persona.getNombre(), persona.getaPaterno(), persona.getaMaterno(), persona.getEmail());
       if (personaO.isPresent()){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseNotFoundDTO(409,"Esta persona o este correo electronico ya existe."));
       }
        Persona personaCreada = personaService.savePersona(persona);
        persona.getHijos().forEach(hijo -> hijo.setPersona(personaCreada));
        persona.getHijos().forEach(hijo -> hijoService.saveHijo(hijo));
        personaCreada.setEdad(Period.between(personaCreada.getFecNacimiento(),LocalDate.now()).getYears());
        return ResponseEntity.status(HttpStatus.CREATED).body(personaCreada);
    }

    @DeleteMapping("/{IdPersona}")
    public ResponseEntity<?> deletePersona (@PathVariable Long IdPersona){
        try{
            Optional<Persona> personaOptional = personaService.findById(IdPersona);
            if (personaOptional.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNotFoundDTO(400,"No se encontró la persona."));
            }else {
                personaOptional.get().setEdad(Period.between(personaOptional.get().getFecNacimiento(),LocalDate.now()).getYears());
                personaOptional.get().getHijos().forEach(hijo -> hijoService.deleteHijo(hijo.getIdHijo()));
                personaService.deletePersonayId(IdPersona);
                return ResponseEntity.status(HttpStatus.OK).body(personaOptional);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseNotFoundDTO(999,e.getMessage()));
        }
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
