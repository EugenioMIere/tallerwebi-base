package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoLote;
import com.tallerwebi.dominio.Lote;
import com.tallerwebi.dominio.RepositorioLote;
import com.tallerwebi.dominio.Sombrero;
import com.tallerwebi.infraestructura.config.HibernateInfrastructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfrastructuraTestConfig.class})
@Transactional
public class RepositorioLoteTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioLote repositorioLote;

    @BeforeEach
    public void init() {
        this.repositorioLote = new RepositorioLoteImpl(sessionFactory);
    }

    //queSePuedaCrearUnLoteConUnCodigoAlfanumericoEstado"No iniciado" que el estado sea un enum
    @Test
    public void queSePuedaCrearUnLoteConUnCodigoAlfanumericoEstadoNoIniciado() {
        Lote lote = new Lote();
        lote.setCodigo("Lote123");
        lote.setEstado(EstadoLote.NO_INICIADO);

        repositorioLote.guardar(lote);

        String hql = "FROM Lote l WHERE l.codigo = :codigo";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("codigo", "Lote123");
        Lote resultado = (Lote) query.getSingleResult();


        assertThat(resultado.getCodigo(), is("Lote123"));
        assertThat(resultado.getEstado(), is(EstadoLote.NO_INICIADO));
        assertThat(resultado, is(lote));
    }
    //dadoQueExistanLotesQueSePuedanTraerTodos
    @Test
    public void dadoQueExistanLotesQueSePuedanTraerTodos() {
        Lote lote1 = new Lote();
        lote1.setCodigo("Lote001");
        lote1.setEstado(EstadoLote.NO_INICIADO);
        this.sessionFactory.getCurrentSession().save(lote1);

        Lote lote2 = new Lote();
        lote2.setCodigo("Lote002");
        lote2.setEstado(EstadoLote.EN_FABRICACION);
        this.sessionFactory.getCurrentSession().save(lote2);

        List<Lote> resultados = repositorioLote.buscarTodos();

        assertThat(resultados.size(), is(2));
        assertThat(resultados.get(0), is(lote1));
    }
    //dadoQueExistanLotesQueSePuedanAvanzarDeEstado
    @Test
    public void dadoQueExistanLotesQueSePuedanAvanzarDeEstado() {
        Lote lote = new Lote();
        lote.setCodigo("Lote003");
        lote.setEstado(EstadoLote.NO_INICIADO);
        this.sessionFactory.getCurrentSession().save(lote);


        lote.setEstado(EstadoLote.EN_FABRICACION);
        repositorioLote.actualizar(lote);

        String hql = "FROM Lote l WHERE l.codigo = :codigo";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("codigo", "Lote003");
        Lote resultado = (Lote) query.getSingleResult();

        assertThat(resultado.getEstado(), is(EstadoLote.EN_FABRICACION));
    }
    //dadoQueExistanLotesQueSePuedanRegistrarSombrerosEnLote
    @Test
    public void dadoQueExistanLotesQueSePuedanRegistrarSombrerosEnLote() {

        Lote lote = new Lote();
        lote.setCodigo("Lote003");
        lote.setEstado(EstadoLote.NO_INICIADO);
        this.sessionFactory.getCurrentSession().save(lote);

        Sombrero sombrero1 = new Sombrero();
        this.sessionFactory.getCurrentSession().save(sombrero1);


        lote.addSombrero(sombrero1);
        repositorioLote.actualizar(lote);

        String hql = "FROM Lote l WHERE l.codigo = :codigo";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("codigo", "Lote003");
        Lote resultado = (Lote) query.getSingleResult();

        assertThat(resultado.getSombreros().size(), is(1));
    }
    //dadoQueExistanLotesQueSePuedanConsultarPorCodigo
    @Test
public void dadoQueExistanLotesQueSePuedanConsultarPorCodigo() {
        Lote lote = new Lote();
        lote.setCodigo("Lote004");
        lote.setEstado(EstadoLote.NO_INICIADO);
        this.sessionFactory.getCurrentSession().save(lote);


        Lote resultado = repositorioLote.buscarPorCodigo("Lote004");

        assertThat(resultado, is(lote));

    }

    //dadoQueExistanLotesQueSePuedanConsultarPorEstadoFinalizado
    @Test
    public void dadoQueExistanLotesQueSePuedanConsultarPorEstadoFinalizado() {
        Lote lote = new Lote();
        lote.setCodigo("Lote005");
        lote.setEstado(EstadoLote.FINALIZADO);
        this.sessionFactory.getCurrentSession().save(lote);

        Lote lote2 = new Lote();
        lote2.setCodigo("Lote006");
        lote2.setEstado(EstadoLote.NO_INICIADO);
        this.sessionFactory.getCurrentSession().save(lote2);

        List<Lote> resultados = repositorioLote.buscarPorestadoFinalizado();

        assertThat(resultados.size(), is(1));
        assertThat(resultados.get(0), is(lote));
    }



}
