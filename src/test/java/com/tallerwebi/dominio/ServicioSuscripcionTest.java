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
        cliente.setTipoSuscripcion("Premium");

        Boolean resultado = servicioSuscripcion.registrarCliente(cliente);


        assertThat(resultado, is(true));

    }
    @Test
    public void dadoQueYaExistaUnClienteRecgistradoConUnDniEntoncesNoPuedoRegistrarUnNuevoClienteConElMismoDni() {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setTipoSuscripcion("Premium");

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setDni(12345678);
        nuevoCliente.setTipoSuscripcion("Básico");

        when(repositorioCliente.obtenerPorDni(12345678)).thenReturn(null)
                .thenReturn(cliente);

        servicioSuscripcion.registrarCliente(cliente);
        Boolean resultado = servicioSuscripcion.registrarCliente(nuevoCliente);

        assertThat(resultado, is(false));

    }
}
