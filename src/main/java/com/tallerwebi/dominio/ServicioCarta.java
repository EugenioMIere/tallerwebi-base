package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.CartaDto;

import javax.transaction.Transactional;

@Transactional
public interface ServicioCarta {
    Boolean crear(CartaDto carta);
}
