package com.utn.repasoFinal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cumpleanito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Se debe proporcionar una fecha del cumpleaños")
    LocalDate fecha;

    @OneToOne
    Persona cumpleaniero;

    @ManyToMany
    Set<Persona>invitados;




}
