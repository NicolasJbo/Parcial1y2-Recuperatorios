package com.utn.repasoFinal.controller;

import com.utn.repasoFinal.model.Cumpleanito;
import com.utn.repasoFinal.model.dto.CumpleanitoDto;
import com.utn.repasoFinal.model.dto.DeudorDto;
import com.utn.repasoFinal.service.CumpleService;
import javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cumple")
public class CumpleController {

    private CumpleService cumpleService;

    @Autowired
    public CumpleController(CumpleService cumpleService) {
        this.cumpleService = cumpleService;
    }

    @PostMapping("/cumpleaniero/{idCumpleaniero}")
    public ResponseEntity addCumple(@PathVariable Integer idCumpleaniero) throws NotFoundException {
        Cumpleanito c = cumpleService.addCumple(idCumpleaniero);

        URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("cumple/{idCumple}")
                .buildAndExpand(c.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{idCumple}")
    public ResponseEntity getCumpleById(@PathVariable Integer idCumple) throws NotFoundException {
        Cumpleanito c = cumpleService.getCumpleById(idCumple);
        return ResponseEntity.ok(CumpleanitoDto.from(c));
    }

    @PutMapping("/{idCumple}/invitado/{idInvitado}")
    public ResponseEntity asignInvitadoToCumple(@PathVariable Integer idCumple,
                                                @PathVariable Integer idInvitado) throws NotFoundException, SizeLimitExceededException {
        cumpleService.asignInvitadoToCumple(idCumple, idInvitado);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{idCumple}/listadoCosto") //solo pagan los jugadores
    public ResponseEntity getListadoCostoPorInvitado(@PathVariable Integer idCumple) throws NotFoundException, IOException, InterruptedException {
        List<DeudorDto> deudoresList = cumpleService.getListadoCostoPorInvitado(idCumple);

        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Elements", String.valueOf(deudoresList.size()))
                .body(deudoresList);
    }
}
