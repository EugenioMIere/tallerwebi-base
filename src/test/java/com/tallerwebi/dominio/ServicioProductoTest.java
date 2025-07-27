package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioProductoTest {
    private ServicioProducto servicioProducto;
    private RepositorioProducto repositorioProducto;

    @BeforeEach
    public void init() {
        this.repositorioProducto = mock(RepositorioProducto.class);
        this.servicioProducto = new ServicioProductoImpl(repositorioProducto);
    }

    @Test
    public void dadoQueIngresoUnaCantiadaDeStockABuscarQueMeDevuelvaTodosLosArticlosQuePoseanMayorStockAlIndicado() {
        // Arrange
        Producto producto = new Producto();
        producto.setNombre("Producto de prueba");
        producto.setStock(11);

        Producto producto1 = new Producto();
        producto1.setNombre("Producto de prueba1");
        producto1.setStock(5);

        Producto producto2 = new Producto();
        producto2.setNombre("Producto de prueba2");
        producto2.setStock(16);

        when(repositorioProducto.buscarPorCantidad(10)).thenReturn(Arrays.asList(producto, producto2));

        // Act
        List<Producto> productosConStockMayorA10 = servicioProducto.buscarPorCantidad(10);

        // Assert
        verify(repositorioProducto).buscarPorCantidad(10);
        assertThat(productosConStockMayorA10.size(), is(2));
        assertThat(productosConStockMayorA10.get(0), is(producto));
        assertThat(productosConStockMayorA10.get(1), is(producto2));
    }
    @Test
    public void dadoQueIngresoUnStockNegativoEntoncesMeDeberaLanzarUnaExcepcion() {
        // Arrange
        int stockNegativo = -5;
        String mensajeEsperado = "El stock debe ser mayor a cero";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            servicioProducto.buscarPorCantidad(stockNegativo);
        });
        // Assert
        assertThat(exception.getMessage(), is(mensajeEsperado));
        verify(repositorioProducto, never()).buscarPorCantidad(stockNegativo);
    }
}
