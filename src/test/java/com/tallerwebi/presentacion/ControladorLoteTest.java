package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoLote;
import com.tallerwebi.dominio.Lote;
import com.tallerwebi.dominio.ServicioLote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorLoteTest {
    private ServicioLote servicioLote;
    private ControladorLote controladorLote;
    private Lote lote;
    private LoteDto loteDto;


    @BeforeEach
    void init() {
        servicioLote = mock(ServicioLote.class);
        controladorLote = new ControladorLote(servicioLote);
        this.lote = mock(Lote.class);
        this.loteDto = new LoteDto(lote);

    }

    //queSePudaAccederALaVistaDeCrearLores
    @Test
    void queSePuedaAccederALaVistaDeCrearLotes() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("crearLote");
        ModelAndView resultado = controladorLote.irACrearLote();
        assert resultado.getViewName().equals(modelAndView.getViewName());
    }


    //queSePuedaCrearUnLoteConDatosValidos
    @Test
    void queSePuedaCrearUnLoteConDatosValidos() {
        loteDto.setCodigo("Lote1");
        loteDto.setEstado(EstadoLote.NO_INICIADO);

        doAnswer(invocation -> {
            Lote loteCreado = invocation.getArgument(0);
            loteCreado.setId(1L);
            return null;
        }).when(servicioLote).crearLote(loteDto.obtenerEntidad());

        ModelAndView modelAndView = controladorLote.crearLote(loteDto);

        // Assert/verificar
        String vistaEsperada = "todosLosLotes";
        String mensajeEsperado = "Lote creado correctamente";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
    }
    //queNoSePuedaCrearUnLoteConDatosInvalidos
    //queSePuedaListarLotes
    /*@Test
    void queSePuedaListarLotes() {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        this.loteDto.set
        modelAndView.setViewName("todosLosLotes");

        when(servicioLote.buscarTodos()).thenReturn(Arrays.asList(this.lote));


        ModelAndView resultado = controladorLote.listarLotes();
        assert resultado.getViewName().equals(modelAndView.getViewName());
    }*/
    //queSePuedaAvanzarElEstadoDeUnLote
    //queSePuedaCargarUnSombreroEnUnLote
    //queSePuedaMostrarTodosLosLotesConestadoFinalizado



}
