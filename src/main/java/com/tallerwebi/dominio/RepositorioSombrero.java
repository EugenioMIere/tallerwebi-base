package com.tallerwebi.dominio;
import com.tallerwebi.dominio.Sombrero;

public interface RepositorioSombrero {

    public Sombrero buscarPorTipo(TipoSombrero tipoSombrero);

    public void guardar(Sombrero sombrero);
}
