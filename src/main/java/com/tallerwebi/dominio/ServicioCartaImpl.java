package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.CartaDto;

public class ServicioCartaImpl implements ServicioCarta {

    @Override
    public Boolean crear(CartaDto carta) {
        return carta.getNombre() != null && !carta.getNombre().isEmpty();
    }
}
