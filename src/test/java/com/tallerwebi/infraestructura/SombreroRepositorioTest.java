package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSombrero;
import com.tallerwebi.dominio.Sombrero;
import com.tallerwebi.dominio.TipoSombrero;
import com.tallerwebi.infraestructura.config.HibernateInfrastructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfrastructuraTestConfig.class})
@Transactional
public class SombreroRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioSombrero repositorioSombrero;

    @BeforeEach
    public void init() {
        this.repositorioSombrero = new RepositorioSombreroImpl(sessionFactory);
    }
    //quesepuedaguardarunsombrero
    @Test
    public void queSePuedaGuardarUnSombrero() {
        Sombrero sombrero = new Sombrero();
        sombrero.setTipo(TipoSombrero.FEDORA);
        sombrero.setPrecio(100.0);

        repositorioSombrero.guardar(sombrero);

        Sombrero resultado = repositorioSombrero.buscarPorTipo(TipoSombrero.FEDORA);
        assertThat(resultado.getPrecio(), is(100.0));
        assertThat(resultado.getTipo().getNombre(), is(TipoSombrero.FEDORA.getNombre()));

    }
    //quesepuedanconsultarsombreroportipo(el tipo es un enum, que conteiene fedor,panama,pala ancha)
    @Test
    public void queSePuedanConsultarSombreroPorTipo() {
        Sombrero sombrero = new Sombrero();
        sombrero.setTipo(TipoSombrero.FEDORA);
        sombrero.setPrecio(100.0);

        sessionFactory.getCurrentSession().save(sombrero);

        Sombrero resultado = repositorioSombrero.buscarPorTipo(TipoSombrero.FEDORA);
        assertThat(resultado.getTipo().getNombre(), is(TipoSombrero.FEDORA.getNombre()));
    }


}
