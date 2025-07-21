package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carta;

import java.util.Collection;

public class CartaDto {
    private Long id;
    private String nombre;

    public CartaDto(Carta carta) {
        this.id = carta.getId();
        this.nombre = carta.getNombre();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Carta obtenerEntidad() {
        Carta carta = new Carta();
        carta.setId(this.id);
        carta.setNombre(this.nombre);
        return carta;
    }
}
