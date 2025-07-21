package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.RepositorioCarta;
import com.tallerwebi.infraestructura.config.HibernateInfrastructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfrastructuraTestConfig.class})
@Transactional
public class RepositorioCartaTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioCarta repositorioCarta;


    @BeforeEach
    public void init() {

        this.repositorioCarta = new RepositorioCartaImpl(this.sessionFactory);
        
    }

    @Test
    @Rollback
    public void cuandoCreoUnaCartaConDatosCorrectosEntoncesSeGuardaEnLaBaseDeDatos() {
        Carta carta = new Carta();
        carta.setNombre("Carta de prueba");

        Boolean guardada = this.repositorioCarta.crear(carta);

        assertThat(guardada, is(true));

    }

    @Test
    public void cuandoObtengoUnaCartaPorIdEntoncesDevuelveLaCartaCorrecta() {
        Carta carta = new Carta();
        carta.setNombre("Carta de prueba");
        this.sessionFactory.getCurrentSession().save(carta);

        Carta obtenida = this.repositorioCarta.obtenerPorId(carta.getId());

        assertThat(obtenida, equalTo(carta));
    }
    @Test
    public void cuandoObtengoTodasLasCartasEntoncesDevuelveUnaListaConTodasLasCartas() {
        Carta carta1 = new Carta();
        carta1.setNombre("Carta 1");
        this.sessionFactory.getCurrentSession().save(carta1);

        Carta carta2 = new Carta();
        carta2.setNombre("Carta 2");
        this.sessionFactory.getCurrentSession().save(carta2);

        var cartas = this.repositorioCarta.obtenerTodasLasCartas();

        assertThat(cartas.size(), is(2));
        assertThat(cartas.contains(carta1), is(true));
        assertThat(cartas.contains(carta2), is(true));
    }
    @Test
    @Rollback
    public void cuandoActualizoUnaCartaEntoncesSeActualizaCorrectamente() {
        Carta carta = new Carta();
        carta.setNombre("Carta original");
        this.sessionFactory.getCurrentSession().save(carta);

        carta.setNombre("Carta actualizada");
        this.repositorioCarta.actualizar(carta);

        Carta actualizada = this.repositorioCarta.obtenerPorId(carta.getId());
        assertThat(actualizada, equalTo(carta));
    }
    @Test
    @Rollback
    public void cuandoEliminoUnaCartaEntoncesSeEliminaCorrectamente() {
        Carta carta = new Carta();
        carta.setNombre("Carta a eliminar");
        this.sessionFactory.getCurrentSession().save(carta);

        this.repositorioCarta.eliminar(carta);

        var cartas = this.repositorioCarta.obtenerTodasLasCartas();

        assertThat(cartas.size(), is(0));
    }

}
