package com.company.admin.product_management.application.product.retrieve.get;

import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductID;

import java.time.Instant;

public record ProductOutput(
        ProductID id,
        Long code,
        String description,
        Instant fabricatedAt,
        Instant expiredAt,
        String supplierCode,
        String supplierDescription,
        String supplierCNPJ,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static ProductOutput from(final Product aProduct) {
        return new ProductOutput(
                aProduct.getId(),
                aProduct.getCode(),
                aProduct.getDescription(),
                aProduct.getFabricatedAt(),
                aProduct.getExpiredAt(),
                aProduct.getSupplierCode(),
                aProduct.getSupplierDescription(),
                aProduct.getSupplierCNPJ(),
                aProduct.isActive(),
                aProduct.getCreatedAt(),
                aProduct.getUpdatedAt(),
                aProduct.getDeletedAt()
        );
    }
}
