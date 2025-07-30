package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoLote;
import com.tallerwebi.dominio.Lote;

public class LoteDto {
    private  Long id;
    private  String codigo;
    private  EstadoLote estado;

    public LoteDto(Lote lote) {
        this.id = lote.getId();
        this.codigo = lote.getCodigo();
        this.estado = lote.getEstado();
    }
    public LoteDto() {
        // Constructor por defecto
    }

    public Lote obtenerEntidad() {
        Lote lote = new Lote();
        lote.setId(this.id);
        lote.setCodigo(this.codigo);
        lote.setEstado(this.estado);
        return lote;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getCodigo() {
        return this.codigo;
    }
    public void setEstado(EstadoLote estado) {
        this.estado = estado;
    }
    public EstadoLote getEstado() {
        return this.estado;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
