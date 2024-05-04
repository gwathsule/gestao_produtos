package com.company.admin.product_management.application.product.update;

import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductID;

public record UpdateProductOutput(
        ProductID id
) {
    public static UpdateProductOutput from(final Product aProduct) {
        return new UpdateProductOutput(aProduct.getId());
    }
}
