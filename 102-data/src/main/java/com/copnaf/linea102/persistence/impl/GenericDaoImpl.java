package com.copnaf.linea102.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.copnaf.linea102.persistence.GenericDao;

/**
 * @author andres
 *
 */
public class GenericDaoImpl<T, PK extends Serializable> implements
        GenericDao<T, PK> {

    private Class<T> type;
    
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public PK save(T o) {
        return (PK) getSession().save(o);
    }

    @SuppressWarnings("unchecked")
    public T getById(PK id) {
        T value = (T) getSession().get(type, id);

        if (value != null && value instanceof HibernateProxy) {
            Hibernate.initialize(value);
            value = (T) ((HibernateProxy) value).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Criteria crit = getSession().createCriteria(type);
        return crit.list();
    }

    public void saveOrUpdate(T o) {
        getSession().saveOrUpdate(o);
    }

    public void update(T o) {
        getSession().update(o);
    }

    public void delete(T o) {
        getSession().delete(o);
    }
    
    public void merge(T o) {
        getSession().merge(o);
    }

}
