package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Cliente {
    private int dni;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoSuscripcion;

    public void setDni(int dni) {

        this.dni = dni;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return dni == cliente.dni;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    public Long getId() {
        return id;
    }

    public int getDni() {
        return dni;
    }

    public void setTipoSuscripcion(String premium) {
        this.tipoSuscripcion = premium;
    }
}
