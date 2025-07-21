package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.ServicioCarta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladoCartaTest {
    private ServicioCarta servicioCarta;
    private ControladorCarta controladorCarta;

    @BeforeEach
    public void setUp() {
        servicioCarta = mock(ServicioCarta.class);
        controladorCarta = new ControladorCarta(servicioCarta);
    }

    @Test
    public void dadoQueSePuedenCrearCartasCuandoCreoUnaObtengoUnMensajeDeExito() {

        // Arrange/preparar
        Carta cartaMock = mock(Carta.class);
        CartaDto carta = new CartaDto(cartaMock);
        carta.setNombre("Carta 1");

        // Simular el comportamiento del servicio
         when(servicioCarta.crear(carta)).thenReturn(true);

        // Act/preparar
        ModelAndView modelAndView= controladorCarta.crearCarta(carta);


        // Assert/verificar
        String vistaEsperada = "crear-carta";
        String mensajeEsperado = "Carta creado correctamente";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(mensajeEsperado, equalTo(modelAndView.getModel().get("mensaje")));
    }

    @Test
    public void dadoQueSePuedenCrearCartasCuandoCreoUnaCartaSinNombreObtengoUnMensajeDeError() {

        Carta cartaMock = mock(Carta.class);
        CartaDto carta = new CartaDto(cartaMock);
        when(servicioCarta.crear(carta)).thenReturn(false);

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
