package com.utn.repasoFinal.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {

    @SerializedName("country_id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("country_code")
    private String code;
    @SerializedName("continent")
    private String continent;
}
