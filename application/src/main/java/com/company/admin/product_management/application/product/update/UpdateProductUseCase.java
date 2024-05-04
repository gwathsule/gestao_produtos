package com.company.admin.product_management.application.product.update;

import com.company.admin.product_management.application.UseCase;
import com.company.admin.product_management.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateProductUseCase
        extends UseCase<UpdateProductCommand, Either<Notification, UpdateProductOutput>> {
}
