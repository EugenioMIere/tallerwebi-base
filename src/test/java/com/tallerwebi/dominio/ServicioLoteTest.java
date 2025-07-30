package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class ServicioLoteTest {
    RepositorioLote repositorioLote;
    ServicioLote servicioLote;
    private Lote lote;
    private Sombrero sombrero;

    @BeforeEach
    public void init() {
        this.repositorioLote = mock(RepositorioLote.class);
        this.servicioLote = new ServicioLoteImpl(repositorioLote);

        this.lote = new Lote();
        this.lote.setCodigo("Lote123");
        this.lote.setEstado(EstadoLote.NO_INICIADO);

        this.sombrero = new Sombrero();
        this.sombrero.setTipo(TipoSombrero.FEDORA);

    }
//queSePuedaCrearUnLoteConUnCodigoAlfanumericoEstadoNoIniciado
    @Test
    public void queSePuedaCrearUnLoteConUnCodigoAlfanumericoEstadoNoIniciado() {

        servicioLote.crearLote(lote);

        verify(repositorioLote, times(1)).guardar(lote);

    }
    //dadoQueExistanLotesQueSePuedanTraerTodos
    @Test
    public void dadoQueExistanLotesQueSePuedanTraerTodos() {

        Lote lote2 = new Lote();
        lote2.setCodigo("Lote456");
        lote2.setEstado(EstadoLote.NO_INICIADO);

        when(repositorioLote.buscarTodos()).thenReturn(Arrays.asList(this.lote,lote2));

        List<Lote> lotes = servicioLote.buscarTodos();

        verify(repositorioLote, times(1)).buscarTodos();
        assertThat(lotes.size(), is(2));

        assertThat(lotes.get(0).getCodigo(), is("Lote123"));
        assertThat(lotes.get(1).getCodigo(), is("Lote456"));


    }



    //dadoQueExistanLotesQueSePuedanAvanzarDeEstado
    @Test
    public void dadoQueExistanLotesQueSePuedanAvanzarDeEstado() {

        when(repositorioLote.buscarPorCodigo("Lote123")).thenReturn(this.lote);

        doAnswer(invocation -> {
            Lote loteActualizado = invocation.getArgument(0);
            loteActualizado.setEstado(EstadoLote.EN_FABRICACION);
            return null;
        }).when(repositorioLote).actualizar(this.lote);

        this.servicioLote.actualizarEstado(this.lote.getCodigo());

        verify(repositorioLote, times(1)).actualizar(this.lote);
        assertThat(this.lote.getEstado(), is(EstadoLote.EN_FABRICACION));

    }

    //dadoQueExistanLotesQueSePuedanConsultarPorCodigo
    @Test
    public void dadoQueExistanLotesQueSePuedanConsultarPorCodigo() {

        when(repositorioLote.buscarPorCodigo("Lote123")).thenReturn(this.lote);

        Lote loteConsultado = servicioLote.buscarLotePorCodigo("Lote123");

        verify(repositorioLote, times(1)).buscarPorCodigo("Lote123");
        assertThat(loteConsultado.getCodigo(), is("Lote123"));
        assertThat(loteConsultado.getEstado(), is(EstadoLote.NO_INICIADO));
    }

    //dadoQueExistanLotesQueSePuedanRegistrarSombrerosEnLote
    @Test
    public void dadoQueExistanLotesQueSePuedanRegistrarSombrerosEnLote() {


        doAnswer(invocation -> {
            this.lote.addSombrero(sombrero);
            return null;
        }).when(repositorioLote).guardar(this.lote);


        when(repositorioLote.buscarPorCodigo("Lote123")).thenReturn(lote);

        servicioLote.cargarSombreroEnLote("Lote123", sombrero);

        Lote loteActualizado = this.servicioLote.buscarLotePorCodigo("Lote123");

        verify(repositorioLote, times(1)).actualizar(this.lote);
        assertThat(loteActualizado.getCodigo(), is("Lote123"));
        assertThat(loteActualizado.getSombreros().size(), is(1));
        assertThat(loteActualizado.getSombreros().get(0).getTipo(), is(TipoSombrero.FEDORA));


    }


    //dadoQueExistanLotesQueSePuedanConsultarPorEstadoFinalizado
    @Test
    public void dadoQueExistanLotesQueSePuedanConsultarPorEstadoFinalizado() {

        Lote lote2 = new Lote();
        lote2.setCodigo("Lote456");
        lote2.setEstado(EstadoLote.FINALIZADO);

        when(repositorioLote.buscarPorestadoFinalizado()).thenReturn(Arrays.asList(lote2));

        List<Lote> lotesFinalizados = servicioLote.buscarTodosLosFinalizados();

        assertThat(lotesFinalizados.size(), is(1));
        assertThat(lotesFinalizados.get(0).getCodigo(), is("Lote456"));

    }



}
