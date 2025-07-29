package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioLote {
    void guardar(Lote lote);

    List<Lote> buscarTodos();

    void actualizar(Lote lote);

    Lote buscarPorCodigo(String lote004);

    List<Lote> buscarPorestadoFinalizado();
}
