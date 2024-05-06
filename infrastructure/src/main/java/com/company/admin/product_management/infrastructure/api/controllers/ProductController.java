package com.company.admin.product_management.infrastructure.api.controllers;

import com.company.admin.product_management.application.product.create.CreateProductCommand;
import com.company.admin.product_management.application.product.create.CreateProductOutput;
import com.company.admin.product_management.application.product.create.CreateProductUseCase;
import com.company.admin.product_management.application.product.retrieve.get.GetProductByCodeUseCase;
import com.company.admin.product_management.application.product.update.UpdateProductCommand;
import com.company.admin.product_management.application.product.update.UpdateProductOutput;
import com.company.admin.product_management.application.product.update.UpdateProductUseCase;
import com.company.admin.product_management.domain.pagination.Pagination;
import com.company.admin.product_management.domain.validation.handler.Notification;
import com.company.admin.product_management.infrastructure.api.ProductAPI;
import com.company.admin.product_management.infrastructure.product.models.CreateProductApiInput;
import com.company.admin.product_management.infrastructure.product.models.ProductApiOutput;
import com.company.admin.product_management.infrastructure.product.models.UpdateProductApiInput;
import com.company.admin.product_management.infrastructure.product.presenters.ProductApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class ProductController implements ProductAPI {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductByCodeUseCase getProductByCodeUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    public ProductController(
            final CreateProductUseCase createProductUseCase,
            final GetProductByCodeUseCase getProductByCodeUseCase,
            final UpdateProductUseCase updateProductUseCase
    ) {
        this.createProductUseCase = Objects.requireNonNull(createProductUseCase);
        this.getProductByCodeUseCase = Objects.requireNonNull(getProductByCodeUseCase);
        this.updateProductUseCase = Objects.requireNonNull(updateProductUseCase);
    }


    @Override
    public ResponseEntity<?> createProduct(final CreateProductApiInput input) {
        final var aCommand = CreateProductCommand.with(
                input.aDescription(),
                input.aFabricatedAt(),
                input.anExpiredAt(),
                input.anSupplierCode(),
                input.aSupplierDescription(),
                input.aSupplierCNPJ(),
                input.isActive() != null ? input.isActive() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<CreateProductOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/products/" + output.productCode().toString())).build();

        return this.createProductUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listProduct(String search, int page, int perPage, String sort, String direction) {
        return null;
    }

    @Override
    public ProductApiOutput getByCode(final String code) {
        return ProductApiPresenter.present(this.getProductByCodeUseCase.execute(Long.parseLong(code)));
    }

    @Override
    public ResponseEntity<?> updateByCode(String code, UpdateProductApiInput input) {
        final var aCommand = UpdateProductCommand.with(
                input.anId(),
                input.aCode(),
                input.aDescription(),
                input.aFabricatedAt(),
                input.anExpiredAt(),
                input.anSupplierCode(),
                input.aSupplierDescription(),
                input.aSupplierCNPJ(),
                input.isActive() != null ? input.isActive() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<UpdateProductOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateProductUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }
}
