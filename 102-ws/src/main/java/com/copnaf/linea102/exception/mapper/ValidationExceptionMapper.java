package com.copnaf.linea102.exception.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.validation.ResponseConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andres.
 */
public class ValidationExceptionMapper implements ExceptionMapper<javax.validation.ValidationException> {
    private static final Logger LOG = LoggerFactory.getLogger(ValidationExceptionMapper.class);

    @SuppressWarnings("rawtypes")
    @Override
    public Response toResponse(javax.validation.ValidationException exception) {
        Response.Status errorStatus = Response.Status.INTERNAL_SERVER_ERROR;
        List<String> errorsList = new ArrayList<String>();
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException constraint = (ConstraintViolationException) exception;
            Iterator i$ = constraint.getConstraintViolations().iterator();
            while (i$.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation) i$.next();
                String errorMessage = this.getPropertyName(violation) + ": " + violation.getMessage();
                errorsList.add(errorMessage);
                LOG.error(violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath() + ": " + violation.getMessage());
            }
            if (!(constraint instanceof ResponseConstraintViolationException)) {
                errorStatus = Response.Status.BAD_REQUEST;
            }
        }
        String errorsAsString = StringUtils.join(errorsList, ", ");
        Response response = Response.status(errorStatus).entity(errorsAsString).build();
        return response;
    }

    @SuppressWarnings("rawtypes")
    private String getPropertyName(ConstraintViolation violation) {
        Iterator iterator = violation.getPropertyPath().iterator();
        Path.Node property = (Path.Node) iterator.next();
        while (iterator.hasNext()) {
            property = (Path.Node) iterator.next();
        }
        return property.getName();
    }

}
