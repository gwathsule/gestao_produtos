package com.company.admin.product_management.application.product.create;

import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductID;

public record CreateProductOutput(
        ProductID id
) {
    public static CreateProductOutput from(final Product aProduct) {
        return new CreateProductOutput(aProduct.getId());
    }
}
