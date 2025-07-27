package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.ServicioSuscripcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ControladorSuscripcionTest {
    private ServicioSuscripcion servicioSuscripcion;
    private ControladorSuscripcion controladorSuscripcion;

    @BeforeEach
    public void setUp() {
        servicioSuscripcion = mock(ServicioSuscripcion.class);
        controladorSuscripcion = new ControladorSuscripcion(servicioSuscripcion);
    }

    @Test
    public void queSeMuestreMensajeDeExitoAlSuscribirse() {
        ModelAndView modelAndView = controladorSuscripcion.irAPaginaSuscripcion("exito");

        String vistaEsperada = "suscripcion";
        assertThat(modelAndView.getViewName(), equalTo(vistaEsperada));
        assertThat(modelAndView.getModel().get("mensaje"), is("Suscripción exitosa"));
    }
    @Test
    public void queNoSeMuestreMensajeSiNoHayExito() {
        ModelAndView modelAndView = controladorSuscripcion.irAPaginaSuscripcion(null);

        String vistaEsperada = "suscripcion";
        assertThat(modelAndView.getViewName(), equalTo(vistaEsperada));
        assertThat(modelAndView.getModel().get("mensaje"), is((Object) null));
    }

    @Test
    public void queSePuedaSuscribirUnCliente() {
        ModelAndView modelAndView = controladorSuscripcion.suscribirCliente(12345678, "Básica");

        String vistaEsperada = "redirect:/suscripcion?exito";
        verify(servicioSuscripcion).suscribirCliente(12345678, "Básica");
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
    }
    @Test
    public void dadoQueUnClienteexistenteYaPoseaMismoTipoDeSuscripcionArrojeError() {
        doAnswer(invocation -> {
            throw new RuntimeException("Cliente ya está suscrito al plan: Básica");
        }).when(servicioSuscripcion).suscribirCliente(12345678, "Básica");

        ModelAndView modelAndView = controladorSuscripcion.suscribirCliente(12345678, "Básica");

        String vistaEsperada = "suscripcion";
        verify(servicioSuscripcion).suscribirCliente(12345678, "Básica");
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
    }
    @Test
    public void queSePuedaObtenerTodosLosClientesPorTipoDeSuscripcion() {
        Cliente cliente = mock(Cliente.class);
        when(cliente.getDni()).thenReturn(12345678);
        when(cliente.getTipoSuscripcion()).thenReturn("Básica");

        when(servicioSuscripcion.ObtenerporTipoSuscripcion("Básica"))
                .thenReturn(List.of(cliente));

        ModelAndView modelAndView = controladorSuscripcion.obtenerporTipoSuscripcion("Básica");

        String vistaEsperada = "historial-suscripcion";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(modelAndView.getModel().get("clientes"), is(List.of(cliente)));
    }
    @Test
    public void queSeRedirijaAObtenerTodosCuandoNoSeleccionoTipoDeSuscripcion() {
        ModelAndView modelAndView = controladorSuscripcion.obtenerporTipoSuscripcion(null);

        String vistaEsperada = "redirect:/obtener-todos";
        assertThat(modelAndView.getViewName(), equalTo(vistaEsperada));
    }
    @Test
    public void dadoQueNoSeEncuentranClientesPorTipoDeSuscripcionCuandoObtengoClientesPorTipoDeSuscripcionEntoncesObtengoUnMensajeDeError() {
        when(servicioSuscripcion.ObtenerporTipoSuscripcion("Premium"))
                .thenThrow(new RuntimeException("No se encontraron clientes con la suscripción: Premium"));

        ModelAndView modelAndView = controladorSuscripcion.obtenerporTipoSuscripcion("Premium");

        String vistaEsperada = "historial-suscripcion";
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(modelAndView.getModel().get("error"), is("No se encontraron clientes con la suscripción: Premium"));
    }

    @Test
    public void dadoQueSePuedeEliminarUnaSuscripcionCuandoEliminoUnaSuscripcionEntoncesRedirijoAObtenerTodos() {
        int dni = 12345678;

        doNothing().when(servicioSuscripcion).eliminarSuscripcion(dni);

        ModelAndView modelAndView = controladorSuscripcion.eliminarSuscripcion(dni);

        String vistaEsperada = "redirect:/obtener-todos";
        verify(servicioSuscripcion).eliminarSuscripcion(dni);
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));

    }
    @Test
    public void dadoQueSePuedenObtenerTodosLosClientesCuandoObtengoTodosLosClientesEntoncesDevuelvoUnaListaDeClientes() {
        Cliente cliente1 = mock(Cliente.class);
        Cliente cliente2 = mock(Cliente.class);
        when(cliente1.getDni()).thenReturn(12345678);
        when(cliente2.getDni()).thenReturn(87654321);
        when(servicioSuscripcion.obtenerTodos()).thenReturn(List.of(cliente1, cliente2));

        ModelAndView modelAndView = controladorSuscripcion.obtenerTodos();

        String vistaEsperada = "historial-suscripcion";
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(modelAndView.getModel().get("clientes"), is(List.of(cliente1, cliente2)));
    }
    @Test
    public void dadoQueNoSeEncuentranClientesCuandoObtengoTodosLosClientesEntoncesObtengoUnMensajeDeError() {
        when(servicioSuscripcion.obtenerTodos()).thenThrow(new RuntimeException("No se encontraron clientes"));

        ModelAndView modelAndView = controladorSuscripcion.obtenerTodos();

        String vistaEsperada = "historial-suscripcion";
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(modelAndView.getModel().get("error"), is("No se encontraron clientes"));
    }

}
