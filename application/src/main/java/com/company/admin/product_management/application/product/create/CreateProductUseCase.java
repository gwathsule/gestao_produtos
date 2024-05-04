package com.company.admin.product_management.application.product.create;

import com.company.admin.product_management.application.UseCase;
import com.company.admin.product_management.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateProductUseCase
        extends UseCase <CreateProductCommand, Either<Notification, CreateProductOutput>> {
}
