package com.utn.repasoFinal.controller;

import com.utn.repasoFinal.model.dto.DeudorDto;
import com.utn.repasoFinal.service.CumpleService;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

public class CumpleControllerTest {

    private CumpleController cumpleController;
    private CumpleService cumpleService;

    @Before
    public void setUp(){
        cumpleService = mock(CumpleService.class);
        cumpleController = new CumpleController(cumpleService);
    }

    @Test
    public void getListadoCostoPorInvitado_Test200() throws InterruptedException, IOException, NotFoundException {
        DeudorDto deudor1 = DeudorDto.builder().debe("150").nombre("Candela").currency("EURO").build();
        DeudorDto deudor2 = DeudorDto.builder().debe("150").nombre("Pamela").currency("DOLAR").build();

        List<DeudorDto> listDeudores= new ArrayList<>();
        listDeudores.add(deudor1); listDeudores.add(deudor2);

        when(cumpleService.getListadoCostoPorInvitado(anyInt())).thenReturn(listDeudores);

        ResponseEntity response = cumpleController.getListadoCostoPorInvitado(4);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Integer.parseInt(response.getHeaders().get("X-Total-Elements").get(0)));
    }
}
