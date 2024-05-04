package com.company.admin.product_management.application.product.update;

import java.time.Instant;

public record UpdateProductCommand(
        String id,
        Long code,
        String description,
        Instant fabricatedAt,
        Instant expiredAt,
        String supplierCode,
        String supplierDescription,
        String supplierCNPJ,
        boolean isActive
) {
    public static UpdateProductCommand with(
            final String anId,
            final Long aCode,
            final String aDescription,
            final Instant aFabricatedAt,
            final Instant anExpiredAt,
            final String anSupplierCode,
            final String aSupplierDescription,
            final String aSupplierCNPJ,
            final boolean isActive
    ) {
        return new UpdateProductCommand(
                anId,
                aCode,
                aDescription,
                aFabricatedAt,
                anExpiredAt,
                anSupplierCode,
                aSupplierDescription,
                aSupplierCNPJ,
                isActive
        );
    }
}
