package com.utn.repasoFinal.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.repasoFinal.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CumpleanitoDto {
    //private String nombre;
    private LocalDate date;
    private String cumpleaniero;
    private List<String> invitados;

    public static CumpleanitoDto from (Cumpleanito c){
        Persona p = c.getCumpleaniero();

        CumpleanitoDto cumpleanitoDto = CumpleanitoDto.builder()
                .date(c.getFecha())
                .cumpleaniero((p.getNombre()+' '+p.getApellido()).toUpperCase())
                .build();

        List<String> list = new ArrayList<>();
        for(Persona x : c.getInvitados()){
            list.add(x.getNombre()+' '+x.getApellido());
        }
        cumpleanitoDto.setInvitados(list);

        return cumpleanitoDto;
    }

}
