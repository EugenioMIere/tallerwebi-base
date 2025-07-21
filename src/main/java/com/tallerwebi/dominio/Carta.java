package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    public Carta() {
    }
    public Carta(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
    public Long getId() {
        return this.id;
    }
}
