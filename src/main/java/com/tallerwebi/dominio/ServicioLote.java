package com.tallerwebi.dominio;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServicioLote {
    void crearLote(Lote lote);

    List<Lote> buscarTodos();

    void actualizarEstado(String loteCodigo);

    void cargarSombreroEnLote(String lote123, Sombrero sombrero1);

    Lote buscarLotePorCodigo(String lote123);

    List<Lote> buscarTodosLosFinalizados();
}
