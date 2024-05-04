package com.company.admin.product_management.application.product.create;

import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateProductUseCase extends CreateProductUseCase {

    private final ProductGateway productGateway;

    public DefaultCreateProductUseCase(ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public CreateProductOutput execute(final CreateProductCommand aCommand) {
        final var aProduct = Product.newProduct(
                aCommand.aCode(),
                aCommand.aDescription(),
                aCommand.aFabricatedAt(),
                aCommand.anExpiredAt(),
                aCommand.anSupplierCode(),
                aCommand.aSupplierDescription(),
                aCommand.aSupplierCNPJ(),
                aCommand.isActive()
        );
        aProduct.validate(new ThrowsValidationHandler());

        return CreateProductOutput.from(this.productGateway.create(aProduct));
    }
}
