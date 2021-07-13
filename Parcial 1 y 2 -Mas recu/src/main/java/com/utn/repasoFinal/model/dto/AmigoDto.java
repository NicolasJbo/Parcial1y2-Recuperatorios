package com.utn.repasoFinal.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.repasoFinal.model.Amigo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AmigoDto extends PersonaDto {
    private String info;

    public static AmigoDto from(Amigo a){
        AmigoDto amigoDto = AmigoDto.builder()
                .info('['+a.getStatusSocial()+"] -> "+ a.getProfesion())
                .build();
        amigoDto.setNombre( (a.getNombre()+' '+a.getApellido()).toUpperCase() );
        amigoDto.setTypePerson(a.typePerson().toString());
        return amigoDto;
    }
}
