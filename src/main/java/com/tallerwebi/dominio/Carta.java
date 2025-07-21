package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cartas")// The table name in the database
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;


    @Transient// This field is not persisted in the database
    private  String color;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return Objects.equals(id, carta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
