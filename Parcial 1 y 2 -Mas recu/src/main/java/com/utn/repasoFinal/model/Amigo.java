package com.utn.repasoFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Amigo extends Persona {

    @NotEmpty(message = "Se debe proporcionar una profesion")
    private String profesion;

    @NotEmpty(message = "Se debe proporcionar un status social")
    private String statusSocial;

    @Override
    public TypePerson typePerson() {
        return TypePerson.AMIGO;
    }

}
