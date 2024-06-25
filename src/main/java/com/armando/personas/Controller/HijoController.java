package com.armando.personas.Controller;

import com.armando.personas.DTOS.ResponseNotFoundDTO;
import com.armando.personas.Models.Hijo;
import com.armando.personas.Models.Persona;
import com.armando.personas.Service.HijoService;
import com.armando.personas.Service.PersonaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/hijos")
public class HijoController {

        @Autowired
        private PersonaService personaService;

        @Autowired
        private HijoService hijoService;

        @PostMapping("/agregar_hijo/{idPersona}")
        public ResponseEntity<?> saveHijos (@Valid @RequestBody Hijo hijo, BindingResult result, @PathVariable Long idPersona){
            if(result.hasErrors()){
                return validar(result);
            }
            Optional <Persona> personaO = personaService.findById(idPersona);
            if (personaO.isEmpty()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseNotFoundDTO(409,"Esta persona no existe."));
            }
            hijo.setPersona(personaO.get());
            hijoService.saveHijo(hijo);
            Optional <Persona> personaBD = personaService.findById(idPersona);
            personaBD.get().setEdad(Period.between(personaBD.get().getFecNacimiento(),LocalDate.now()).getYears());
            return ResponseEntity.status(HttpStatus.CREATED).body(personaBD);
        }

        @DeleteMapping("/eliminar_hijo/{idhijo}")
        public ResponseEntity<?> deleteHijo (@PathVariable Long idhijo){
            try{
                Optional<Hijo> hijoO = hijoService.findById(idhijo);
                if (hijoO.isEmpty()){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNotFoundDTO(400,"No se encontró el hijo."));
                }else {
                    hijoService.deleteHijo(idhijo);
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseNotFoundDTO(400,"Hijo eliminado correctamente."));
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
