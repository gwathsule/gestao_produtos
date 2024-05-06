package com.company.admin.product_management.application.product.create;

import com.company.admin.product_management.domain.product.Product;

public record CreateProductOutput(
        Long productCode
) {
    public static CreateProductOutput from(final Long productCode) {
        return new CreateProductOutput(productCode);
    }

    public static CreateProductOutput from(final Product aProduct) {
        return new CreateProductOutput(aProduct.getCode());
    }
}
