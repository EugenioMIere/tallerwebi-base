package com.tallerwebi.dominio;

public enum EstadoLote {
    NO_INICIADO("No iniciado"),
    EN_FABRICACION("En fabricación"),
    FINALIZADO("Finalizado"),;

    private final String nombre;

    EstadoLote(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
