package com.utn.repasoFinal.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.repasoFinal.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepresentanteDto extends PersonaDto {
    //private String nombre;
    private String pesoBoveda;
    private String montoTotal;
    private List<String> jugadores;
    private List<String> amigos;

    public static RepresentanteDto from (Representante r){
        RepresentanteDto representanteDto = RepresentanteDto.builder()
                                                .montoTotal( (r.getMontoTotal()!=null)? '$'+String.format("%.2f", r.getMontoTotal()):null )
                                                .pesoBoveda( (r.getPesoBoveda()!=null)? String.format("%.2f", r.getPesoBoveda())+"kg" :null )
                                                .build();
        representanteDto.setNombre( (r.getNombre()+' '+r.getApellido()).toUpperCase() );
        representanteDto.setTypePerson(TypePerson.REPRESENTANTE.name());

        List<String> listJug = new ArrayList<>();
        for(Jugador j : r.getJugadores()){
            listJug.add(j.getNombre()+' '+j.getApellido());
        }
        representanteDto.setJugadores(listJug);

        List<String> list = new ArrayList<>();
        for(Amigo a : r.getAmigos()){
            list.add(a.getNombre()+' '+a.getApellido());
        }
        representanteDto.setAmigos(list);
        return representanteDto;
    }
}
