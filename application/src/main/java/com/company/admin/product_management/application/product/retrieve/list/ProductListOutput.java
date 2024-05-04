package com.company.admin.product_management.application.product.retrieve.list;

import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductID;

import java.time.Instant;

public record ProductListOutput(
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
    public static ProductListOutput from(final Product aProduct) {
        return new ProductListOutput(
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
