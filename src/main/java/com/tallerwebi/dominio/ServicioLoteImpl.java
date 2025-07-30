package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioLoteImpl implements ServicioLote {
    private final RepositorioLote repositorioLote;

    public ServicioLoteImpl(RepositorioLote repositorioLote) {
        this.repositorioLote = repositorioLote;
    }

    @Override
    public void crearLote(Lote lote) {
        this.repositorioLote.guardar(lote);
    }

    @Override
    public List<Lote> buscarTodos() {
        List<Lote> lotes = this.repositorioLote.buscarTodos();
        if (lotes.isEmpty()) {
            throw new RuntimeException("No se encontraron lotes");
        }
        return lotes;
    }

    @Override
    public void actualizarEstado(String loteCodigo) {
        Lote lote = this.repositorioLote.buscarPorCodigo(loteCodigo);

        if (lote == null) {
            throw new RuntimeException("Lote no encontrado");
        }

        if (lote.getEstado() == EstadoLote.NO_INICIADO) {
            lote.setEstado(EstadoLote.EN_FABRICACION);
        }else {
            if (lote.getEstado() == EstadoLote.EN_FABRICACION) {
                lote.setEstado(EstadoLote.FINALIZADO);
            } else {
                throw new RuntimeException("El lote ya está finalizado");
            }
        }
        this.repositorioLote.actualizar(lote);

    }

    @Override
    public void cargarSombreroEnLote(String lote123, Sombrero sombrero1) {
        Lote lote = this.repositorioLote.buscarPorCodigo(lote123);
        if (lote == null) {
            throw new RuntimeException("Lote no encontrado");
        }
        lote.addSombrero(sombrero1);
        this.repositorioLote.actualizar(lote);
    }

    @Override
    public Lote buscarLotePorCodigo(String lote123) {
        Lote lote = this.repositorioLote.buscarPorCodigo(lote123);
        if (lote == null) {
            throw new RuntimeException("Lote no encontrado");
        }
        return lote;
    }

    @Override
    public List<Lote> buscarTodosLosFinalizados() {
        List<Lote> lotesFinalizados = this.repositorioLote.buscarPorestadoFinalizado();
        if (lotesFinalizados.isEmpty()) {
            throw new RuntimeException("No se encontraron lotes finalizados");
        }
        return lotesFinalizados;
    }
}
