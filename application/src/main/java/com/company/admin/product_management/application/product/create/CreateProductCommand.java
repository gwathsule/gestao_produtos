package com.company.admin.product_management.application.product.create;

import java.time.Instant;

public record CreateProductCommand(
        String aDescription,
        Instant aFabricatedAt,
        Instant anExpiredAt,
        String anSupplierCode,
        String aSupplierDescription,
        String aSupplierCNPJ,
        boolean isActive
) {
    public static CreateProductCommand with(
            final String aDescription,
            final Instant aFabricatedAt,
            final Instant anExpiredAt,
            final String anSupplierCode,
            final String aSupplierDescription,
            final String aSupplierCNPJ,
            final boolean isActive
    ) {
        return new CreateProductCommand(
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
