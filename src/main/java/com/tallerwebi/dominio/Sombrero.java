package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
@Table
public class Sombrero {

    @Column
    private TipoSombrero tipo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double precio;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public double getPrecio() {
        return precio;
    }

    public TipoSombrero getTipo() {
        return tipo;
    }

    public void setTipo(TipoSombrero tipoSombrero) {
        tipo = tipoSombrero;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
