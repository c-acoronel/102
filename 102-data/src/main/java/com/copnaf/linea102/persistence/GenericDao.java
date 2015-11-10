package com.copnaf.linea102.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * @author andres
 */
public interface GenericDao<T, PK extends Serializable> {

    T getById(PK id);

    List<T> getAll();

    void update(T persistentObject);

    void saveOrUpdate(T persistentObject);

    void delete(T persistentObject);

    PK save(T persistentObject);

    void merge(T persistentObject);

}