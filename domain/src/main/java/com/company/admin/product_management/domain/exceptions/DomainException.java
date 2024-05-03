package com.company.admin.product_management.domain.exceptions;

import com.company.admin.product_management.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException{

    protected final List<Error> errors;

    private DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(final Error anErrors) {
        return new DomainException(anErrors.message() ,List.of(anErrors));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("" , anErrors);
    }


    public List<Error> getErrors() {
        return errors;
    }
}
