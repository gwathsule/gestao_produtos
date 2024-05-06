package com.company.admin.product_management.infrastructure.product.presenters;

import com.company.admin.product_management.application.product.retrieve.get.ProductOutput;
import com.company.admin.product_management.infrastructure.product.models.ProductApiOutput;

public interface ProductApiPresenter {

    static ProductApiOutput present(final ProductOutput output) {
        return new ProductApiOutput(
                output.id().getValue(),
                output.code().toString(),
                output.description(),
                output.fabricatedAt(),
                output.expiredAt(),
                output.supplierCode(),
                output.supplierDescription(),
                output.supplierCNPJ(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
