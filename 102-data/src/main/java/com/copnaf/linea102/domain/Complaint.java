package com.copnaf.linea102.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;


/**
 * @author Andres
 */
@Entity()
@Table(name = "complaint")
public class Complaint {

    public static final int FIELD_LENGTH = 100;

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue
    private Long id;

    @Column(name = "description", length = FIELD_LENGTH)
    private String description;

    @Column(name = "date", length = FIELD_LENGTH)
    private Date date;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Person denunciante;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Person involucrado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Person getDenunciante() {
        return denunciante;
    }

    public void setDenunciante(Person denunciante) {
        this.denunciante = denunciante;
    }

    public Person getInvolucrado() {
        return involucrado;
    }

    public void setInvolucrado(Person involucrado) {
        this.involucrado = involucrado;
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
        Complaint rhs = (Complaint) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.description, rhs.description)
                .append(this.date, rhs.date)
                .append(this.denunciante, rhs.denunciante)
                .append(this.involucrado, rhs.involucrado)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(description)
                .append(date)
                .append(denunciante)
                .append(involucrado)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("description", description)
                .append("date", date)
                .append("denunciante", denunciante)
                .append("involucrado", involucrado)
                .toString();
    }
}
