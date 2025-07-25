package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

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

        servicioSuscripcion.registrarCliente(cliente);
        when(repositorioCliente.obtenerPorDni(cliente.getDni())).thenReturn(cliente);
        Cliente clienteBuscado = servicioSuscripcion.obtenerClientePorDni(cliente.getDni());
        //enviar en el metodo dni y la suscripcion que se quiere contratar, sino siemnpre devuelve verdadero

        clienteBuscado.setTipoSuscripcion("Premium");
        servicioSuscripcion.actualizarCliente(clienteBuscado);

        Cliente clienteActualizado = servicioSuscripcion.obtenerClientePorDni(clienteBuscado.getDni());

        assertThat(clienteActualizado.getTipoSuscripcion(), is("Premium"));

    }

    //probar que no se pueda contratar una suscripcion si si el cliente ya tiene una suscripcion activa del mismo tipo

    //
}
