package com.utn.repasoFinal.controller;

import com.utn.repasoFinal.api.PromesasApiService;
import com.utn.repasoFinal.model.JugadorPromesa;
import com.utn.repasoFinal.model.Persona;
import com.utn.repasoFinal.model.dto.PersonaDto;
import com.utn.repasoFinal.service.PersonaService;
import javassist.NotFoundException;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    PersonaService personaService;
    PromesasApiService promesasApiService;

    @Autowired
    public PersonaController(PersonaService personaService, PromesasApiService promesasApiService) {
        this.personaService = personaService;
        this.promesasApiService = promesasApiService;
    }

    @PostMapping
    public ResponseEntity addPersona(@RequestBody Persona persona){
        Persona p = personaService.addPersona(persona);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idPersona}")
                .buildAndExpand(p.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{idPersona}")
    public ResponseEntity getPersonaById(@PathVariable Integer idPersona) throws NotFoundException {
        Persona p = personaService.getPersonaById(idPersona);
        return ResponseEntity.ok(p);
    }

    @GetMapping
    public ResponseEntity getAllPersonas(@RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "5") Integer size,
                                                        @RequestParam(defaultValue = "id") String sortField1,
                                                        @RequestParam(defaultValue = "nombre") String sortField2){
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, sortField1));
        orders.add(new Sort.Order(Sort.Direction.ASC, sortField2));

        Page<PersonaDto> personas = personaService.getAllPersonas(page, size, orders);

        if(personas.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Long.toString(personas.getTotalElements()))
                    .header("X-Total-Pages", Long.toString(personas.getTotalPages()))
                    .header("X-Actual-Page", Integer.toString(page))
                    .body(personas.getContent());
    }

    @DeleteMapping("/{idPersona}")
    public ResponseEntity deletePersonaByID(@PathVariable Integer idPersona) throws NotFoundException {
        personaService.deletePersonaByID(idPersona);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/representante/{idRepresentante}/jugador/{idJugador}")
    public ResponseEntity asignarJugadorARepresentante(@PathVariable Integer idRepresentante,
                                                      @PathVariable Integer idJugador) throws NotFoundException {
        personaService.asignJugadorToRepresentante(idRepresentante, idJugador);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/representante/{idRepresentante}/amigo/{idAmigo}")
    public ResponseEntity asignarAmigoARepresentante(@PathVariable Integer idRepresentante,
                                                      @PathVariable Integer idAmigo) throws NotFoundException, SizeLimitExceededException {
        personaService.asignAmigoToRepresentante(idRepresentante, idAmigo);
        return ResponseEntity.accepted().build();
    }


    @PutMapping("/jugador/{idJugador}/currency/{idCurrency}")
    public ResponseEntity asignarCurrencyaJugador(@PathVariable Integer idJugador,
                                                 @PathVariable Integer idCurrency) throws NotFoundException {
        personaService.asignCurrencyToJugador(idJugador, idCurrency);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/promesas")
    public ResponseEntity getJugadoresPromesa() throws IOException, InterruptedException {

        List<JugadorPromesa> jugadores = promesasApiService.getJugadoresPromesas();

        if(jugadores.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(jugadores.size()))
                    .body(jugadores);
    }

}
