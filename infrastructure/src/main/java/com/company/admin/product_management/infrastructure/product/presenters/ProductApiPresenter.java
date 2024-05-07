package com.company.admin.product_management.infrastructure.product.presenters;

import com.company.admin.product_management.application.product.retrieve.get.ProductOutput;
import com.company.admin.product_management.application.product.retrieve.list.ProductListOutput;
import com.company.admin.product_management.infrastructure.product.models.ProductResponse;
import com.company.admin.product_management.infrastructure.product.models.ProductListResponse;

public interface ProductApiPresenter {

    static ProductResponse present(final ProductOutput output) {
        return new ProductResponse(
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

    static ProductListResponse present(final ProductListOutput output) {
        return new ProductListResponse(
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
                output.deletedAt()
        );
    }
}
