package com.utn.repasoFinal.controller;

import com.utn.repasoFinal.api.PromesasApiService;
import com.utn.repasoFinal.model.JugadorPromesa;
import com.utn.repasoFinal.service.PersonaService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonaControllerTest {

    PersonaController personaController;
    PersonaService personaService;
    PromesasApiService promesasApiService;

    @Before
    public void setUp(){
        personaService = mock(PersonaService.class);
        promesasApiService = mock(PromesasApiService.class);
        personaController = new PersonaController(personaService, promesasApiService);
    }

    @Test
    public void getJugadoresPromesa_Test200() throws IOException, InterruptedException {
        JugadorPromesa jugador1 = JugadorPromesa.builder().id("4").age(18).firstname("Lautaro").lastname("Fullone").build();
        JugadorPromesa jugador2 = JugadorPromesa.builder().id("2").age(21).firstname("Nicolas").lastname("Bertuccio").build();
        List<JugadorPromesa> listJugadores = new ArrayList<>();
        listJugadores.add(jugador1);
        listJugadores.add(jugador2);

        when(promesasApiService.getJugadoresPromesas()).thenReturn(listJugadores);

        ResponseEntity<List<JugadorPromesa>> response = personaController.getJugadoresPromesa();

        assertEquals(false,response.getBody().isEmpty());
        assertEquals("2", response.getHeaders().get("X-Total-Elements").get(0));
        assertEquals("Nicolas" , response.getBody().get(1).getFirstname());
    }

    @Test
    public void getJugadoresPromesa_Test204() throws IOException, InterruptedException {


        when(promesasApiService.getJugadoresPromesas()).thenReturn(Collections.emptyList());

        ResponseEntity<List<JugadorPromesa>> response = personaController.getJugadoresPromesa();

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }
}
