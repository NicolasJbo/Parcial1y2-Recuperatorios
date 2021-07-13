package com.utn.repasoFinal.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.repasoFinal.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyDto {
    private String typeCurrency;
    private Double monto;

    public static CurrencyDto from (Currency c){
        return CurrencyDto.builder()
                .typeCurrency(c.getTypeCurrency().name())
                .monto(c.getMonto())
                .build();
    }
}
