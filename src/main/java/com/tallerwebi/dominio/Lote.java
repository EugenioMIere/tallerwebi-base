package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(unique = true)
    private String codigo;

    @Column
    private EstadoLote estado;

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    private List<Sombrero> sombreros;


    public List<Sombrero> getSombreros() {
        return sombreros;
    }

    public void addSombrero(Sombrero sombrero) {
        if (sombreros == null) {
            sombreros = new LinkedList<>();
        }
        sombreros.add(sombrero);
        sombrero.setLote(this);
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCodigo(String codigoLote) {
        this.codigo = codigoLote;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setEstado(EstadoLote estadoLote) {
        this.estado = estadoLote;
    }

    public EstadoLote getEstado() {
        return estado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Lote lote = (Lote) o;
        return Objects.equals(id, lote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
