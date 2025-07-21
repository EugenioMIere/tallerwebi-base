package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.CartaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCartaImpl implements ServicioCarta {

    private final RepositorioCarta repositorioCarta;

    @Autowired
    public ServicioCartaImpl(RepositorioCarta repositorioCarta) {
        this.repositorioCarta = repositorioCarta;
    }

    @Override
    public Boolean crear(CartaDto carta) {

        return this.repositorioCarta.crear(carta.obtenerEntidad());
    }
}
