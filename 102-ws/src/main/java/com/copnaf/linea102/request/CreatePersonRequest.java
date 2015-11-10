package com.copnaf.linea102.request;

import com.copnaf.linea102.domain.PersonType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Andres.
 */
public class CreatePersonRequest {

    @NotEmpty private String name;
    @NotEmpty private String lastName;
    private String address;
    private String dni;
    private String phoneNumber;
    @NotNull private PersonType personType;


    @JsonCreator
    public CreatePersonRequest(@JsonProperty("name") final String name,
                               @JsonProperty("lastName") final String lastName,
                               @JsonProperty("address") final String address,
                               @JsonProperty("dni") final String dni,
                               @JsonProperty("phoneNumber") final String phoneNumber,
                               @JsonProperty("personType") final PersonType personType){
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.personType = personType;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getDni() {
        return dni;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PersonType getPersonType() {
        return personType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("lastName", lastName)
                .append("address", address)
                .append("dni", dni)
                .append("phoneNumber", phoneNumber)
                .append("personType", personType)
                .toString();
    }
}
