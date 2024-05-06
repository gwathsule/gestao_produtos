package com.company.admin.product_management.domain.exceptions;

import com.company.admin.product_management.domain.AggregateRoot;
import com.company.admin.product_management.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException{

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Long code
    ){
        final var errorMessage = "%s with code %s was not found".formatted(
                anAggregate.getSimpleName(),
                code.toString()
        );
        return new NotFoundException(errorMessage, Collections.emptyList());
    }
}
