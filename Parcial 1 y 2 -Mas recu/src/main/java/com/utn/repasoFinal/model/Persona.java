package com.utn.repasoFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, property="typePerson", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Jugador.class, name = "JUGADOR"),
        @JsonSubTypes.Type(value = Representante.class, name = "REPRESENTANTE"),
        @JsonSubTypes.Type(value = Amigo.class, name = "AMIGO"),

})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Se debe proporcionar un nombre")
    private String nombre;

    @NotEmpty(message = "Se debe proporcionar un apellido")
    private String apellido;

    @AccessType(AccessType.Type.PROPERTY)
    public abstract TypePerson typePerson();

    @JsonIgnore
    @ManyToMany
    Set<Cumpleanito>cumpleanitos;



}
