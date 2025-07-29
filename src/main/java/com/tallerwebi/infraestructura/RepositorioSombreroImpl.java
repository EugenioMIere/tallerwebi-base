package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSombrero;
import com.tallerwebi.dominio.Sombrero;
import com.tallerwebi.dominio.TipoSombrero;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class RepositorioSombreroImpl implements RepositorioSombrero {

    private SessionFactory sessionFactory;

    public RepositorioSombreroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Sombrero buscarPorTipo(TipoSombrero tipoSombrero) {
        String hql = "FROM Sombrero s WHERE s.tipo = :tipo";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("tipo", tipoSombrero);
        Sombrero sombrero = (Sombrero) query.getSingleResult();
        return sombrero;

    }

    @Override
    public void guardar(Sombrero sombrero) {
        sessionFactory.getCurrentSession().save(sombrero);
    }
}
