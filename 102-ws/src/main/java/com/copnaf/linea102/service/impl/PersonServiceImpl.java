package com.copnaf.linea102.service.impl;

import com.copnaf.linea102.domain.Person;
import com.copnaf.linea102.domain.PersonType;
import com.copnaf.linea102.persistence.IPersonDao;
import com.copnaf.linea102.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Andres.
 */
@Service("personService")
public class PersonServiceImpl implements IPersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private IPersonDao personDao;

    @Override
    @Transactional
    public Long create(final String name, final String lastName, final String dni, final String address,
                       final String phoneNumber, final PersonType personType) {
        LOGGER.debug("Creating new person. name={}, lastName={}, personType={}", name, lastName, personType);

        Person person = new Person();
        person.setName(name);
        person.setLastName(lastName);
        person.setDni(dni);
        person.setAddress(address);
        person.setPhoneNumber(phoneNumber);
        person.setType(personType);

        return personDao.save(person);
    }

    @Override
    public void update(Person person) {

    }

    @Override
    public void delete(Long personId) {

    }

    @Override
    public void saveOrUpdate(Person person) {

    }

    @Override
    public List<Person> getPersonById() {
        return null;
    }

    @Override
    public Person getPersonById(Long personId) {
        return null;
    }
}
