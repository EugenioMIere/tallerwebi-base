package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCartaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ControladoCartaTest {
    private ServicioCartaImpl servicioCarta;
    private ControladorCarta controladorCarta;

    @BeforeEach
    public void setUp() {
        servicioCarta = new ServicioCartaImpl();
        controladorCarta = new ControladorCarta(servicioCarta);
    }

    @Test
    public void dadoQueSePuedenCrearCartasCuandoCreoUnaObtengoUnMensajeDeExito() {

        // Arrange/preparar
        CartaDto carta = new CartaDto();
        carta.setNombre("Carta 1");

        // Act/preparar
        ModelAndView modelAndView= controladorCarta.crearCarta(carta);


        // Assert/verificar
        String vistaEsperada = "crear-carta";
        String mensajeEsperado = "Carta creado correctamente";

        modelAndView.getViewName();
        modelAndView.getModel();
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(mensajeEsperado, equalTo(modelAndView.getModel().get("mensaje")));
    }

    @Test
    public void dadoQueSePuedenCrearCartasCuandoCreoUnaCartaSinNombreObtengoUnMensajeDeError() {

        // Arrange/preparar
        CartaDto carta = new CartaDto();
        carta.setNombre("");

        // Act/preparar
        ModelAndView modelAndView= controladorCarta.crearCarta(carta);


        // Assert/verificar
        String vistaEsperada = "crear-carta";
        String mensajeEsperado = "Error al crear la carta";

        modelAndView.getViewName();
        modelAndView.getModel();
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(mensajeEsperado, equalTo(modelAndView.getModel().get("mensaje")));
    }
}
