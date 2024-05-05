package com.company.admin.product_management.application.product.update;

import com.company.admin.product_management.domain.exceptions.DomainException;
import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.validation.Error;
import com.company.admin.product_management.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateProductUseCase extends UpdateProductUseCase{

    private final ProductGateway productGateway;

    public DefaultUpdateProductUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public Either<Notification, UpdateProductOutput> execute(final UpdateProductCommand aCommand) {

        final var code = aCommand.code();
        final var aProduct = this.productGateway.findByCode(code).orElseThrow(notFound(code));
        final var notification = Notification.create();

        aProduct.update(
                aCommand.code(),
                aCommand.description(),
                aCommand.fabricatedAt(),
                aCommand.expiredAt(),
                aCommand.supplierCode(),
                aCommand.supplierDescription(),
                aCommand.supplierCNPJ(),
                aCommand.isActive()
        ).validate(notification);

        return notification.hasError() ? Left(notification) : update(aProduct);
    }

    private Either<Notification, UpdateProductOutput> update(final Product aProduct) {
        return Try(() -> this.productGateway.update(aProduct))
                .toEither()
                .bimap(Notification::create, UpdateProductOutput::from);
    }

    private Supplier<DomainException> notFound(Long aCode) {
        return () -> DomainException.with(new Error("Product with code %s was not found".formatted(aCode.toString())));
    }
}
