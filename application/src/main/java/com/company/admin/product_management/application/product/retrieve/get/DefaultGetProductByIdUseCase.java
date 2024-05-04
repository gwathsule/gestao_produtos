package com.company.admin.product_management.application.product.retrieve.get;

import com.company.admin.product_management.domain.exceptions.DomainException;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.product.ProductID;
import com.company.admin.product_management.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetProductByIdUseCase extends GetProductByIDUseCase{

    private final ProductGateway productGateway;

    public DefaultGetProductByIdUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public ProductOutput execute(String anId) {
        final var anProductId = ProductID.from(anId);

        return this.productGateway.findById(anProductId)
                .map(ProductOutput::from)
                .orElseThrow(notFound(anProductId));
    }

    private Supplier<DomainException> notFound(ProductID anId) {
        return () -> DomainException.with(new Error("Product with ID %s was not found".formatted(anId.getValue())));
    }
}
