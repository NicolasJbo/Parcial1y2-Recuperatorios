package com.utn.repasoFinal.service;

import com.utn.repasoFinal.api.CotizacionApiService;
import com.utn.repasoFinal.model.*;
import com.utn.repasoFinal.model.dto.DeudorDto;
import com.utn.repasoFinal.repository.CumpleRepository;
import com.utn.repasoFinal.repository.PersonaRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CumpleServiceTest {
    private CumpleService cumpleService;
    private CumpleRepository cumpleRepository;
    private PersonaService personaService;
    private PersonaRepository personaRepository;
    private CotizacionApiService cotizacionApiService;

    @Before
    public void setUp(){
        cumpleRepository = mock(CumpleRepository.class);
        personaService = mock(PersonaService.class);
        personaRepository = mock(PersonaRepository.class);
        cotizacionApiService = mock(CotizacionApiService.class);
        cumpleService = new CumpleService(cotizacionApiService, cumpleRepository, personaService, personaRepository);
    }

    @Test
    public void getListadoCostoPorInvitado_Test200() throws NotFoundException, IOException, InterruptedException {
        Representante representante = Representante.builder().build();
        Currency currencyDolar = Currency.builder().typeCurrency(TypeCurrency.DOLAR).build();
        Currency currencyEuro = Currency.builder().typeCurrency(TypeCurrency.EURO).build();

        Persona jugador1 = Jugador.builder().currency(currencyDolar).minutosJugados(150).altura(175).peso(50D).build();
        Persona jugador2 = Jugador.builder().currency(currencyEuro).minutosJugados(160).altura(145).peso(64D).build();

        Set<Persona> invitados = new HashSet<>();
        invitados.add(jugador1); invitados.add(jugador2);

        Cumpleanito cumpleanito = Cumpleanito.builder().invitados(invitados).cumpleaniero(representante).fecha(LocalDate.now()).build();

        when(cumpleRepository.findById(anyInt())).thenReturn(Optional.of(cumpleanito));
        when(cotizacionApiService.getCotizacionDolar()).thenReturn(150D);
        when(cotizacionApiService.getCotizacionEuro()).thenReturn(100D);

        List<DeudorDto> deudores = cumpleService.getListadoCostoPorInvitado(4);

        assertEquals(2, deudores.size() );
        assertEquals(String.valueOf(250D), (deudores.get(0).getDebe()));
        //el index es 0 porq al venir de un set los indices no son exactos

    }
}
