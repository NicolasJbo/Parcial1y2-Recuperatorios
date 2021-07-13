package com.utn.repasoFinal.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.repasoFinal.model.Currency;
import com.utn.repasoFinal.model.Jugador;
import com.utn.repasoFinal.model.TypeCurrency;
import com.utn.repasoFinal.model.TypePerson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JugadorDto extends PersonaDto{
    //private String nombre;
    private String fechaNacimiento;
    private Double peso;
    private Integer altura;
    private Integer goles;
    private Integer minutosJugados;
    private String sueldo;

    public static JugadorDto from (Jugador j){

        JugadorDto jugadorDto = JugadorDto.builder()
                                    .fechaNacimiento(j.getFechaNacimiento())
                                    .peso(j.getPeso())
                                    .altura(j.getAltura())
                                    .goles(j.getGoles())
                                    .minutosJugados(j.getMinutosJugados())
                                    .sueldo('['+j.getCurrency().typeCurrency.toString()+"] -> "+ j.getCurrency().getMonto())
                                    .build();
        jugadorDto.setNombre( (j.getNombre()+' '+j.getApellido()).toUpperCase() );
        jugadorDto.setTypePerson(j.typePerson().toString());
        return jugadorDto;
    }

}
