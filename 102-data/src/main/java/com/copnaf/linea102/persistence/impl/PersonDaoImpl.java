package com.copnaf.linea102.persistence.impl;

import com.copnaf.linea102.domain.Person;
import com.copnaf.linea102.persistence.IPersonDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Andres.
 */
@Repository
public class PersonDaoImpl extends GenericDaoImpl<Person, Long> implements IPersonDao {
}
