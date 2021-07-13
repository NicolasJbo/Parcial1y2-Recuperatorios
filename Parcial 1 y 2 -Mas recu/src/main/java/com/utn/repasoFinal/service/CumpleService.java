package com.utn.repasoFinal.service;

import com.utn.repasoFinal.api.CotizacionApiService;
import com.utn.repasoFinal.model.Cumpleanito;
import com.utn.repasoFinal.model.Jugador;
import com.utn.repasoFinal.model.Persona;
import com.utn.repasoFinal.model.TypeCurrency;
import com.utn.repasoFinal.model.dto.DeudorDto;
import com.utn.repasoFinal.repository.CumpleRepository;
import com.utn.repasoFinal.repository.PersonaRepository;
import javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CumpleService {

    private CumpleRepository cumpleRepository;
    private PersonaService personaService;
    private PersonaRepository personaRepository;
    private CotizacionApiService cotizacionApiService;

    @Autowired
    public CumpleService(CotizacionApiService cotizacionApiService, CumpleRepository cumpleRepository, PersonaService personaService, PersonaRepository personaRepository) {
        this.cumpleRepository = cumpleRepository;
        this.personaService = personaService;
        this.personaRepository = personaRepository;
        this.cotizacionApiService = cotizacionApiService;
    }

    public Cumpleanito addCumple(Integer idCumpleaniero) throws NotFoundException {
        Persona p = personaService.getPersonaById(idCumpleaniero);
        Cumpleanito cumpleanito = new Cumpleanito();
        LocalDate date = LocalDate.now();

        cumpleanito.setFecha(date);
        cumpleanito.setCumpleaniero(p);

        return cumpleRepository.save(cumpleanito);
    }

    public Cumpleanito getCumpleById(Integer idCumple) throws NotFoundException {
        return cumpleRepository.findById(idCumple)
                .orElseThrow(()-> new NotFoundException("No se encontro el registo "+idCumple+" en la tabla CUMPLEANIOS"));
    }

    public void asignInvitadoToCumple(Integer idCumple, Integer idInvitado) throws NotFoundException, SizeLimitExceededException {
        Cumpleanito c = getCumpleById(idCumple);
        Set<Persona> invitados =  c.getInvitados();

        if(invitados.size() >= 5)
            throw new SizeLimitExceededException("No se pudo agregar el registro "+idInvitado+" al cumple. Se exceden los limites de invitados", invitados.size(), 10);

        Persona p = personaService.getPersonaById(idInvitado);
        Set<Cumpleanito>cumplesPersona = p.getCumpleanitos();
        cumplesPersona.add(c);

        invitados.add(p);
        personaRepository.save(p);
        cumpleRepository.save(c);
    }

    public List<DeudorDto> getListadoCostoPorInvitado(Integer idCumple) throws NotFoundException, IOException, InterruptedException {
        Cumpleanito c = getCumpleById(idCumple);
        Set<Persona>invitados = c.getInvitados();
        List<DeudorDto> deudores = new ArrayList<>();

        Double debePagar;
        Double cotizacionDolar = cotizacionApiService.getCotizacionDolar();
        Double cotizacionEuro = cotizacionApiService.getCotizacionEuro();
        Double valorParticipacion =25000D;

        for (Persona p : invitados){
            if(p instanceof Jugador){ //solo pagan los jugadores
                Jugador jugador = (Jugador) p;
                TypeCurrency typeCurrencyJugador = jugador.getCurrency().getTypeCurrency();
                if(typeCurrencyJugador == TypeCurrency.EURO)
                    debePagar = valorParticipacion / cotizacionEuro;
                else
                    debePagar = valorParticipacion / cotizacionDolar;
                deudores.add(DeudorDto.from(jugador,debePagar));
            }
        }
        return deudores;

    }
}
