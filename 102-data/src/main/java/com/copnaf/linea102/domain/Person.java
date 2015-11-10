package com.copnaf.linea102.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * @author Andres.
 */
@Entity()
@Table(name = "person")
public class Person {

    public static final int FIELD_LENGTH = 100;

    @Id
    @Column(name = "id", unique=true)
    @GeneratedValue
    private Long id;

    @Column(name = "name", length=FIELD_LENGTH)
    private String name;

    @Column(name = "last_name", length=FIELD_LENGTH)
    private String lastName;

    @Column(name = "dni", length=FIELD_LENGTH)
    private String dni;

    @Column(name = "address", length=FIELD_LENGTH)
    private String address;

    @Column(name = "phone_number", length=FIELD_LENGTH)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "person_type", length=FIELD_LENGTH)
    private PersonType type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Person rhs = (Person) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.name, rhs.name)
                .append(this.lastName, rhs.lastName)
                .append(this.dni, rhs.dni)
                .append(this.address, rhs.address)
                .append(this.phoneNumber, rhs.phoneNumber)
                .append(this.type, rhs.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .append(lastName)
                .append(dni)
                .append(address)
                .append(phoneNumber)
                .append(type)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("lastName", lastName)
                .append("dni", dni)
                .append("address", address)
                .append("phoneNumber", phoneNumber)
                .append("type", type)
                .toString();
    }
}
