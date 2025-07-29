package com.tallerwebi.dominio;

public enum TipoSombrero {
    FEDORA("Fedora"),
    PANAMA("Panamá"),
    PALA_ANCHA("Pala ancha");

    private final String nombre;

    TipoSombrero(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
