package com.copnaf.linea102.persistence.impl;

import com.copnaf.linea102.domain.Person;
import com.copnaf.linea102.persistence.AbstractDaoTest;
import com.copnaf.linea102.persistence.IPersonDao;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Andres.
 */
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/data/person.xml")
public class PersonDaoImplTest extends AbstractDaoTest<Person, Long> {

    private static final Long PERSON_ID = 1L;

    @Autowired
    private IPersonDao personDao;

    @BeforeMethod
    public void setUp() {
        this.setDao(personDao);
    }

    @Test
    @Transactional
    public void searchAllTest() {
        Assert.assertTrue(searchAll().size() == 2);
    }

    @Test
    @Transactional
    public void getByIdTest() {
        Assert.assertNotNull(getById(1L));
    }

    @Test
    @Transactional
    public void saveOrUpdateTest() {
        Person instance = getById(PERSON_ID);
        instance.setName("New Name");
        saveOrUpdate(instance);
        Person instance2 = getById(PERSON_ID);
        Assert.assertEquals(instance.getName(), instance2.getName());
    }

    @Test
    @Transactional
    public void updateTest() {
        Person person = getById(PERSON_ID);
        person.setName("new Name");
        update(person);
        Person updatedPerson = getById(PERSON_ID);
        Assert.assertEquals(person.getName(), updatedPerson.getName());
    }

    @Test
    @Transactional
    public void deleteTest() {
        Person person = getById(PERSON_ID);
        delete(person);
        Assert.assertNull(getById(PERSON_ID));
    }

    @Test
    @Transactional
    public void saveTest() {
        Person instance = getById(PERSON_ID);
        instance.setId(null);
        Long id = save(instance);
        Assert.assertNotNull(getById(id));
    }
}
