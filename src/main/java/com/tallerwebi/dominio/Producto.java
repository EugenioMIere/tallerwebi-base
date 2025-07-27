package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Producto {

    @Column
    private String nombre;

    @Column
    private int stock;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setNombre(String nombreProducto) {
        this.nombre = nombreProducto;
    }
    public String getNombre() {
        return nombre;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getStock() {
        return stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
