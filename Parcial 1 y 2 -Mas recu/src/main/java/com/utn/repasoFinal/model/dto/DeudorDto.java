package com.utn.repasoFinal.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.repasoFinal.model.Jugador;
import com.utn.repasoFinal.model.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeudorDto {
    private String nombre;
    private String currency;
    private String debe;

    public static DeudorDto from(Jugador j, Double aPagar){
        return DeudorDto.builder()
                .nombre( (j.getNombre()+' '+j.getApellido()) )
                .currency(j.getCurrency().getTypeCurrency().name())
                .debe(String.valueOf(aPagar))
                .build();
    }

}
