package com.utn.repasoFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Jugador extends Persona {

    @DecimalMin(value = "20", message = "El peso ingresado debe ser mayor que 20kg")
    private Double peso;

    @Min(value=60, message = "La altura ingresada debe ser mayor que 60cm")
    private Integer altura;

    @Min(value=0, message="La cantidad de goles debe ser mayor/igual que 0")
    private Integer goles;

    @Min(value=0, message="La cantidad de minutos debe ser mayor/igual que 0")
    private Integer minutosJugados;

    @NotNull(message = "Se debe proporcionar una fecha de nacimiento")
    private String fechaNacimiento;

    @OneToOne(cascade = CascadeType.ALL) //si borras un jugador se borra el currency tmb
    @JoinColumn(name="id_currency")
    private Currency currency;

    @Override
    public TypePerson typePerson() {
        return TypePerson.JUGADOR;
    }

    @JsonIgnore
    @ManyToOne
    private Representante representante;
}
