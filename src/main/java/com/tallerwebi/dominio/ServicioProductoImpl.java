package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioProductoImpl implements ServicioProducto {
    private RepositorioProducto repositorioProducto;

    public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public List<Producto> buscarPorCantidad(int stock) {

        if (stock >= 0) {
            List<Producto> productos = this.repositorioProducto.buscarPorCantidad(stock);
            if (!productos.isEmpty()) {
                return productos;
            } else {
                throw new RuntimeException("No se encontraron productos con stock mayor a " + stock);
            }
        }
        throw new IllegalArgumentException("El stock consultado debe ser mayor a cero");
    }
}
