package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCarta {
    Boolean crear(Carta carta);
    Carta obtenerPorId(Long id);
    List<Carta> obtenerTodasLasCartas();
    void actualizar(Carta carta);
    void eliminar(Carta carta);
}
