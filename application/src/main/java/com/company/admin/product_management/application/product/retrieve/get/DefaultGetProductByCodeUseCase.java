package com.company.admin.product_management.application.product.retrieve.get;

import com.company.admin.product_management.domain.exceptions.DomainException;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetProductByCodeUseCase extends GetProductByCodeUseCase {

    private final ProductGateway productGateway;

    public DefaultGetProductByCodeUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public ProductOutput execute(Long aCode) {

        return this.productGateway.findByCode(aCode)
                .map(ProductOutput::from)
                .orElseThrow(notFound(aCode));
    }

    private Supplier<DomainException> notFound(Long aCode) {
        return () -> DomainException.with(new Error("Product with code %s was not found".formatted(aCode.toString())));
    }
}
