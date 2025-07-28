package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.ServicioProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ControladorProductoTest {
    private ControladorProducto controladorProducto;
    private ServicioProducto servicioProducto;

    @BeforeEach
    public void init() {
        servicioProducto = mock(ServicioProducto.class);
        controladorProducto = new ControladorProducto(servicioProducto);
    }

    @Test
    public void dadoQueSePuedaIrAPaginaProductosEntoncesSePuedaObtenerUnaVistaDeProductos() {
        ModelAndView vistaEsperada = new ModelAndView("productos");

        ModelAndView vistaObtenida = controladorProducto.irAPaginaProductos();

        assertThat(vistaObtenida.getViewName(), is(vistaEsperada.getViewName()));
        assertThat(vistaObtenida.getModel().size(), is(0));
    }


    @Test
    public void dadoQueSePuedaBuscarProductosMayoresAUnStockEntoncesSePuedaObtenerUnaListaDeProductosPorPathParam() {
        // Arrange
        int stock = 10;
        Producto producto1 = new Producto();
        producto1.setNombre("Producto 1");
        producto1.setStock(15);

        Producto producto2 = new Producto();
        producto2.setNombre("Producto 2");
        producto2.setStock(20);

        when(servicioProducto.buscarPorCantidad(stock)).thenReturn(Arrays.asList(producto1, producto2));

        // Act
        ModelAndView productos = controladorProducto.buscarStockMayoresPorPathParam(stock);

        // Assert
        verify(servicioProducto, times(1)).buscarPorCantidad(stock);
        assertThat(productos.getModel().get("productos"), is(Arrays.asList(producto1, producto2)));
        assertThat(productos.getViewName(), is("productos"));
        assertThat(productos.getModel().size(), is(1));

    }
    @Test
    public void dadoQueSePuedaBuscarProductosMayoresAUnStockSiIngresoUnaCantidadNoExistenteArrojaErrorNoSeEncontraronConMayorStockPorPathParam() {

        int stock = 10;

        doAnswer(invocation -> {
            throw new RuntimeException("No se encontraron productos con stock mayor a " + stock);
        }).when(servicioProducto).buscarPorCantidad(stock);

        // Act
        ModelAndView vistaEsperada = controladorProducto.buscarStockMayoresPorPathParam(stock);

        // Assert
        verify(servicioProducto, times(1)).buscarPorCantidad(stock);
        assertThat(vistaEsperada.getViewName(), is("productos"));
        assertThat(vistaEsperada.getModel().get("error"), is("No se encontraron productos con stock mayor a 10"));
    }
    @Test
    public void dadoQueSePuedaBuscarProductosMayoresAUnStockSiIngresoUnaCantidadNegativaLanzaExcepcionPorPathParam() {

        int stock = -1;

        doAnswer(invocation -> {
            throw new IllegalArgumentException("El stock consultado debe ser mayor a cero");
        }).when(servicioProducto).buscarPorCantidad(stock);

        // Act
        ModelAndView vistaEsperada = controladorProducto.buscarStockMayoresPorPathParam(stock);

        // Assert
        verify(servicioProducto, times(1)).buscarPorCantidad(stock);
        assertThat(vistaEsperada.getViewName(), is("productos"));
        assertThat(vistaEsperada.getModel().get("error"), is("El stock consultado debe ser mayor a cero"));
    }


    @Test
    public void dadoQueSePuedaBuscarProductosMayoresAUnStockEntoncesSePuedaObtenerUnaListaDeProductosPorPost() {
        // Arrange
        int stock = 10;
        Producto producto1 = new Producto();
        producto1.setNombre("Producto 1");
        producto1.setStock(15);

        Producto producto2 = new Producto();
        producto2.setNombre("Producto 2");
        producto2.setStock(20);

        when(servicioProducto.buscarPorCantidad(stock)).thenReturn(Arrays.asList(producto1, producto2));

        // Act
        ModelAndView productos = controladorProducto.buscarStockMayoresPorPost(stock);

        // Assert
        verify(servicioProducto, times(1)).buscarPorCantidad(stock);
        assertThat(productos.getModel().get("productos"), is(Arrays.asList(producto1, producto2)));
        assertThat(productos.getViewName(), is("productos"));
        assertThat(productos.getModel().size(), is(1));

    }
    @Test
    public void dadoQueSePuedaBuscarProductosMayoresAUnStockSiIngresoUnaCantidadNoExistenteArrojaErrorNoSeEncontraronConMayorStockPorPost() {

        int stock = 10;

        doAnswer(invocation -> {
            throw new RuntimeException("No se encontraron productos con stock mayor a " + stock);
        }).when(servicioProducto).buscarPorCantidad(stock);

        // Act
        ModelAndView vistaEsperada = controladorProducto.buscarStockMayoresPorPost(stock);

        // Assert
        verify(servicioProducto, times(1)).buscarPorCantidad(stock);
        assertThat(vistaEsperada.getViewName(), is("productos"));
        assertThat(vistaEsperada.getModel().get("error"), is("No se encontraron productos con stock mayor a 10"));
    }

    @Test
    public void dadoQueSePuedaBuscarProductosMayoresAUnStockSiIngresoUnaCantidadNegativaLanzaExcepcionPorPost() {

        int stock = -1;

        doAnswer(invocation -> {
            throw new IllegalArgumentException("El stock consultado debe ser mayor a cero");
        }).when(servicioProducto).buscarPorCantidad(stock);

        // Act
        ModelAndView vistaEsperada = controladorProducto.buscarStockMayoresPorPost(stock);

        // Assert
        verify(servicioProducto, times(1)).buscarPorCantidad(stock);
        assertThat(vistaEsperada.getViewName(), is("productos"));
        assertThat(vistaEsperada.getModel().get("error"), is("El stock consultado debe ser mayor a cero"));
    }
}
