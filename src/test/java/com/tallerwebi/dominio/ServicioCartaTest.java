package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.CartaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.*;

public class ServicioCartaTest {

    private RepositorioCarta repositorioCarta ;
    private ServicioCarta servioCarta;

    @BeforeEach
    public void init() {
       this.repositorioCarta = mock(RepositorioCarta.class);
       this.servioCarta = new ServicioCartaImpl(this.repositorioCarta);
    }
    @Test
    public void cuadnoCreoUnaCartaCOmpletaEntoncesObtengoUnResultadoPositivo(){
        when(repositorioCarta.crear(any())).thenReturn(true);

        Boolean creado = this.servioCarta.crear(mock(CartaDto.class));

        assertThat(creado, is(true));

    }
}
