package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoLote;
import com.tallerwebi.dominio.Lote;
import com.tallerwebi.dominio.RepositorioLote;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioLoteImpl implements RepositorioLote {
    private SessionFactory sessionFactory;

    public RepositorioLoteImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Lote lote) {
        sessionFactory.getCurrentSession().save(lote);
    }

    @Override
    public List<Lote> buscarTodos() {
        String hql = "FROM Lote";
        return sessionFactory.getCurrentSession().createQuery(hql, Lote.class).getResultList();
    }

    @Override
    public void actualizar(Lote lote) {
        sessionFactory.getCurrentSession().update(lote);
    }

    @Override
    public Lote buscarPorCodigo(String lote004) {
        String hql = "FROM Lote l WHERE l.codigo = :codigo";
        Query query = sessionFactory.getCurrentSession().createQuery(hql, Lote.class);
        query.setParameter("codigo", lote004);

        Lote resultado = (Lote) query.getSingleResult();

        return resultado;
    }

    @Override
    public List<Lote> buscarPorestadoFinalizado() {
        String hql = "FROM Lote l WHERE l.estado = :estado";
        Query query = sessionFactory.getCurrentSession().createQuery(hql, Lote.class);
        query.setParameter("estado", EstadoLote.FINALIZADO);

        List<Lote> resultado = query.getResultList();

        return resultado;
    }
}
