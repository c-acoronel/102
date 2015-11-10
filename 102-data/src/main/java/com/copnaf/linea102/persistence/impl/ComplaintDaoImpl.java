package com.copnaf.linea102.persistence.impl;

import com.copnaf.linea102.domain.Complaint;
import com.copnaf.linea102.persistence.IComplaintDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Andres.
 */
@Repository
public class ComplaintDaoImpl extends GenericDaoImpl<Complaint, Long> implements IComplaintDao {
}
