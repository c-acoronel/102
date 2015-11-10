package com.copnaf.linea102.persistence;

import java.io.Serializable;
import java.util.List;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Andres
 */
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public abstract class AbstractDaoTest<T, PK extends Serializable> extends AbstractTestNGSpringContextTests {

    private GenericDao<T, PK> dao;

    @Transactional
    public T getById(PK id) {
        return dao.getById(id);
    }

    @Transactional
    public void update(T obj) {
        dao.update(obj);
    }

    @Transactional
    public List<T> searchAll() {
        return dao.getAll();
    }

    @Transactional
    public void saveOrUpdate(T obj) {
        dao.saveOrUpdate(obj);
    }

    @Transactional
    public void delete(T obj) {
        dao.delete(obj);
    }

    @Transactional
    public PK save(T obj) {
        return dao.save(obj);
    }

    public void setDao(GenericDao<T, PK> dao) {
        this.dao = dao;
    }

}