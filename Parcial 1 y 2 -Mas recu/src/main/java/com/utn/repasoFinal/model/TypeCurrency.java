package com.utn.repasoFinal.model;

public enum TypeCurrency {

    DOLAR("USD", 150.5F),
    EURO("EUR", 175.2F);

    private String description;
    private Float cotizacion;

    TypeCurrency(String description ,Float cotizacion) {
        this.description = description;
        this.cotizacion=cotizacion;
    }

    public String getDescription() {
        return description;
    }

    public Float getCotizacion() {
        return cotizacion;
    }

    public static TypeCurrency find(final String value) {
        for (TypeCurrency n : values()) {
            if (n.toString().equalsIgnoreCase(value)) {
                return n;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid TypeCurrency: %s", value));
    }
}

