package com.copnaf.linea102.service;

import com.copnaf.linea102.domain.Person;
import com.copnaf.linea102.domain.PersonType;

import java.util.List;

/**
 * @author Andres.
 */
public interface IPersonService {

    Long create(final String name, final String lastName, final String dni, final String address,
                final String phoneNumber, final PersonType personType);

    void update(Person person);

    void delete(Long personId);

    void saveOrUpdate(Person person);

    List<Person> getPersonById();

    Person getPersonById(Long personId);
}
