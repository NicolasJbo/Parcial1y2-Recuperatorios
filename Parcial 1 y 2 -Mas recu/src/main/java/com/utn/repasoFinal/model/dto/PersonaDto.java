package com.utn.repasoFinal.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.utn.repasoFinal.model.Amigo;
import com.utn.repasoFinal.model.Jugador;
import com.utn.repasoFinal.model.Persona;
import com.utn.repasoFinal.model.Representante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonaDto {
    private String nombre;
    private String typePerson;

    public static PersonaDto from (Persona p){
        if(p instanceof Representante)
            return RepresentanteDto.from( (Representante) p);
        else if(p instanceof Jugador)
            return JugadorDto.from( (Jugador) p);
        else
            return AmigoDto.from( (Amigo) p);

    }
}
