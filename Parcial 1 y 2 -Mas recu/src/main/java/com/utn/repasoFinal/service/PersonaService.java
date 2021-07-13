package com.utn.repasoFinal.service;

import com.utn.repasoFinal.model.*;
import com.utn.repasoFinal.model.dto.PersonaDto;
import com.utn.repasoFinal.repository.PersonaRepository;
import javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class PersonaService {

    PersonaRepository personaRepository;
    CurrencyService currencyService;

    @Autowired
    public PersonaService(PersonaRepository personaRepository, CurrencyService currencyService) {
        this.personaRepository = personaRepository;
        this.currencyService = currencyService;
    }

    public Persona addPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public Persona getPersonaById(Integer idPersona) throws NotFoundException {
        return personaRepository.findById(idPersona)
                .orElseThrow(()-> new NotFoundException("No se encontro el registo "+idPersona+" en la tabla PERSONAS"));
    }

    public Page<PersonaDto> getAllPersonas(Integer page, Integer size, List<Sort.Order> orders) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<Persona>personas = personaRepository.findAll(pageable);

        Page<PersonaDto> personasDto =  Page.empty();
        if(!personas.isEmpty())
            personasDto = personas.map(p -> PersonaDto.from(p));

        return personasDto;
    }
    //para test hay q retornar un string 'deleted'
    public void deletePersonaByID(Integer idPersona) throws NotFoundException {
        if(!personaRepository.existsById(idPersona))
            throw new NotFoundException("No se encontro el registo "+idPersona+" en la tabla PERSONAS");

        personaRepository.deleteById(idPersona);
    }

    public void asignJugadorToRepresentante(Integer idRepresentante, Integer idJugador) throws NotFoundException {
        Representante r = (Representante) getPersonaById(idRepresentante);
        Jugador j = (Jugador) getPersonaById(idJugador);

        j.setRepresentante(r);

        List<Jugador> listJugadores = r.getJugadores();
        listJugadores.add(j);
        r.setJugadores(listJugadores);

        r.calcularDinero(); //hace los calculos
        personaRepository.save(r);
        personaRepository.save(j);
    }

    public void asignCurrencyToJugador(Integer idJugador, Integer idCurrency) throws NotFoundException {
        Jugador j = (Jugador) getPersonaById(idJugador);
        Currency c = currencyService.getCurrencyById(idCurrency);

        j.setCurrency(c);
        personaRepository.save(j);
    }

    public void asignAmigoToRepresentante(Integer idRepresentante, Integer idAmigo) throws NotFoundException, SizeLimitExceededException {
        Representante r = (Representante) getPersonaById(idRepresentante);
        Amigo a = (Amigo) getPersonaById(idAmigo);

        List<Amigo> listAmigos = r.getAmigos();
        listAmigos.add(a);
        r.setAmigos(listAmigos);

        personaRepository.save(r);
    }
}
