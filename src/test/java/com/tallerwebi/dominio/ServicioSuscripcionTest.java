package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioSuscripcionTest {
    private ServicioSuscripcion servicioSuscripcion;
    private RepositorioCliente repositorioCliente;

    @BeforeEach
    public void init() {
        repositorioCliente = mock(RepositorioCliente.class);
        servicioSuscripcion = new ServicioSuscripcionImpl(repositorioCliente);
    }


    @Test
    public void queSePuedaRegistrarUnClienteConDni() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);

        Boolean resultado = servicioSuscripcion.registrarCliente(cliente);


        assertThat(resultado, is(true));

    }
    @Test
    public void dadoQueExistaUnClientePuedaObtenerloPorDni() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Básica");

        when(repositorioCliente.obtenerPorDni(cliente.getDni())).thenReturn(cliente);
        Cliente clienteBuscado = servicioSuscripcion.obtenerClientePorDni(cliente.getDni());

        assertThat(clienteBuscado.getDni(), is(12345678));
        assertThat(clienteBuscado.getTipoSuscripcion(), is("Básica"));

    }
    @Test
    public void dadoQueYaExistaUnClienteRecgistradoConUnDniEntoncesNoPuedoRegistrarUnNuevoClienteConElMismoDni() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setDni(12345678);

        when(repositorioCliente.obtenerPorDni(12345678)).thenReturn(null)
                .thenReturn(cliente);

        servicioSuscripcion.registrarCliente(cliente);
        Boolean resultado = servicioSuscripcion.registrarCliente(nuevoCliente);

        assertThat(resultado, is(false));

    }
    @Test
    public void dadoQueExistaUnClientePuedaVerSuTipoDeSuscripcion() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Básica");

        when(repositorioCliente.obtenerPorDni(12345678)).thenReturn(cliente);
        Cliente clienteActualizado = servicioSuscripcion.obtenerClientePorDni(cliente.getDni());

        assertThat(clienteActualizado.getTipoSuscripcion(), is("Básica"));

    }

    @Test
    public void dadoQueExistaUnClientePuedaContratarUnaNuevaSuscripcion() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);

        when(repositorioCliente.obtenerPorDni(cliente.getDni())).thenReturn(cliente);
        doAnswer(invocation -> {
            cliente.setTipoSuscripcion("Premium");
            return null;
        }).when(repositorioCliente).suscribir(cliente.getDni(), "Premium");

        servicioSuscripcion.suscribirCliente(cliente.getDni(), "Premium");
        Cliente clienteActualizado = servicioSuscripcion.obtenerClientePorDni(cliente.getDni());

        verify(repositorioCliente).suscribir(12345678, "Premium");
        assertThat(clienteActualizado.getTipoSuscripcion(), is("Premium"));
    }


    @Test
    public void dadoQueExistaUnClienteConUnaSuscripcionActivaDelMismoTipoNoPuedaContratarUnaNuevaSuscripcion() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Básica");

        when(repositorioCliente.obtenerPorDni(cliente.getDni())).thenReturn(cliente);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioSuscripcion.suscribirCliente(cliente.getDni(), "Básica");
        });

        assertThat(exception.getMessage(), is("Cliente ya está suscrito al plan: Básica"));
        verify(repositorioCliente, never()).suscribir(cliente.getDni(), "Básica");
    }

    @Test
    public void dadoQueExistaUnClienteConUnaSuscripcionActivaDeDistintoTipoPuedaContratarUnaNuevaSuscripcion() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Básica");

        when(repositorioCliente.obtenerPorDni(cliente.getDni())).thenReturn(cliente);

        doAnswer(invocation -> {
            cliente.setTipoSuscripcion("Premium");
            return null;
        }).when(repositorioCliente).suscribir(cliente.getDni(), "Premium");

        Cliente clienteBuscado = servicioSuscripcion.obtenerClientePorDni(cliente.getDni());

        String status = servicioSuscripcion.suscribirCliente(clienteBuscado.getDni(), "Premium");
        Cliente clienteActualizado = servicioSuscripcion.obtenerClientePorDni(clienteBuscado.getDni());

        verify(repositorioCliente).suscribir(12345678, "Premium");
        assertThat(status, is("Suscripción ok"));
        assertThat(clienteActualizado.getTipoSuscripcion(), is("Premium"));
    }

    @Test
    public void quePuedaObtenerClientePorTipoSuscripcion() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Básica");

        when(repositorioCliente.obtenerPorTipoSuscripcion("Básica")).
                thenReturn(Arrays.asList(cliente));
        List<Cliente> clientes = servicioSuscripcion.ObtenerporTipoSuscripcion("Básica");

        assertThat(clientes.size(), is(1));
        assertThat(clientes.get(0).getDni(), is(12345678));
    }

    @Test
    public void quePuedaObtenerClientesPorTipoSuscripcion() {
        Cliente cliente1 = new Cliente();
        cliente1.setDni(12345678);
        cliente1.setTipoSuscripcion("Básica");

        Cliente cliente2 = new Cliente();
        cliente2.setDni(87654321);
        cliente2.setTipoSuscripcion("Básica");

        when(repositorioCliente.obtenerPorTipoSuscripcion("Básica"))
                .thenReturn(Arrays.asList(cliente1, cliente2));
        List<Cliente> clientes = servicioSuscripcion.ObtenerporTipoSuscripcion("Básica");

        assertThat(clientes.size(), is(2));
        assertThat(clientes.get(0).getDni(), is(12345678));
        assertThat(clientes.get(1).getDni(), is(87654321));
    }
    //eliminar suscripción de un cliente
    @Test
    public void dadoQueExistaUnClientePuedaEliminarSuSuscripcion() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Básica");

        when(repositorioCliente.obtenerPorDni(cliente.getDni())).thenReturn(cliente);
        doAnswer(invocation -> {
            cliente.setTipoSuscripcion(null);
            return null;
        }).when(repositorioCliente).eliminar(cliente.getDni());

        servicioSuscripcion.eliminarSuscripcion(cliente.getDni());
        Cliente clienteActualizado = servicioSuscripcion.obtenerClientePorDni(cliente.getDni());

        verify(repositorioCliente).eliminar(cliente.getDni());
        assertThat(clienteActualizado.getTipoSuscripcion(), is(nullValue()));

    }

    //obtener todos los clientes
    @Test
    public void quePuedaObtenerTodosLosClientes() {

        Cliente cliente1 = new Cliente();
        cliente1.setDni(12345678);
        cliente1.setTipoSuscripcion("Básica");

        Cliente cliente2 = new Cliente();
        cliente2.setDni(87654321);
        cliente2.setTipoSuscripcion("Premium");

        when(repositorioCliente.obtenerTodos()).thenReturn(Arrays.asList(cliente1, cliente2));
        List<Cliente> clientes = servicioSuscripcion.obtenerTodos();

        assertThat(clientes.size(), is(2));
        assertThat(clientes.get(0).getDni(), is(12345678));
        assertThat(clientes.get(1).getDni(), is(87654321));
    }


}
