package com.utn.repasoFinal.model;

public enum TypePerson {
    REPRESENTANTE("Un representante"),
    JUGADOR("Un jugador"),
    AMIGO("Un amigo");

    private String description;

    TypePerson(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
