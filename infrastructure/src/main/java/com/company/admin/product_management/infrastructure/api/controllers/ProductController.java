package com.company.admin.product_management.infrastructure.api.controllers;

import com.company.admin.product_management.application.product.create.CreateProductCommand;
import com.company.admin.product_management.application.product.create.CreateProductOutput;
import com.company.admin.product_management.application.product.create.CreateProductUseCase;
import com.company.admin.product_management.application.product.retrieve.get.GetProductByCodeUseCase;
import com.company.admin.product_management.domain.pagination.Pagination;
import com.company.admin.product_management.domain.validation.handler.Notification;
import com.company.admin.product_management.infrastructure.api.ProductAPI;
import com.company.admin.product_management.infrastructure.product.models.CreateProductApiInput;
import com.company.admin.product_management.infrastructure.product.models.ProductApiOutput;
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

    public ProductController(
            final CreateProductUseCase createProductUseCase,
            final GetProductByCodeUseCase getProductByCodeUseCase
    ) {
        this.createProductUseCase = Objects.requireNonNull(createProductUseCase);
        this.getProductByCodeUseCase = Objects.requireNonNull(getProductByCodeUseCase);
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
}
