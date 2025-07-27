package com.tallerwebi.dominio;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServicioProducto {
    List<Producto> buscarPorCantidad(int stock);
}
