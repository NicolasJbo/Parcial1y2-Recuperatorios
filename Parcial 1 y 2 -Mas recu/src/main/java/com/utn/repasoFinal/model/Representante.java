package com.utn.repasoFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Representante extends Persona{

    @DecimalMin(value="0.0", message = "El peso ingresado debe ser mayor/igual que 0kg")
    private Double pesoBoveda;

    @DecimalMin(value="0.0", message = "El monto ingresado debe ser mayor/igual que $0")
    private Double montoTotal;

    @Override
    public TypePerson typePerson() {
        return TypePerson.REPRESENTANTE;
    }

    @OneToMany
    private List<Jugador> jugadores;

    @OneToMany
    private List<Amigo> amigos;

    public void calcularDinero() {
        Double acumulador = 0D;
        Currency currencyJugador;
        for(Jugador j : getJugadores()){
            currencyJugador = j.getCurrency();
            acumulador += currencyJugador.getMonto() * currencyJugador.getTypeCurrency().getCotizacion();
        }
        this.montoTotal = acumulador;
        this.pesoBoveda = acumulador/100;
    }
}
