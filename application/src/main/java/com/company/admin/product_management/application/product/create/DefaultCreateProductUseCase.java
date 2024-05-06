package com.company.admin.product_management.application.product.create;

import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;

public class DefaultCreateProductUseCase extends CreateProductUseCase {

    private final ProductGateway productGateway;

    public DefaultCreateProductUseCase(ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public Either<Notification, CreateProductOutput> execute(final CreateProductCommand aCommand) {
        final var aProduct = Product.newProduct(
                123L,
                aCommand.aDescription(),
                aCommand.aFabricatedAt(),
                aCommand.anExpiredAt(),
                aCommand.anSupplierCode(),
                aCommand.aSupplierDescription(),
                aCommand.aSupplierCNPJ(),
                aCommand.isActive()
        );

        final var notification = Notification.create();
        aProduct.validate(notification);

        return notification.hasError() ? Left(notification) : create(aProduct);
    }

    private Either<Notification, CreateProductOutput> create(final Product aProduct) {
        return API.Try(() -> this.productGateway.create(aProduct))
                .toEither()
                .bimap(Notification::create, CreateProductOutput::from);
    }

}
