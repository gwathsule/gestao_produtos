package com.company.admin.product_management.application.product.retrieve.list;

import com.company.admin.product_management.domain.pagination.Pagination;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.product.ProductSearchQuery;

import java.util.Objects;

public class DefaultListProductsUseCase extends ListProductsUseCase{

    private final ProductGateway productGateway;

    public DefaultListProductsUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public Pagination<ProductListOutput> execute(ProductSearchQuery aQuery) {
        return this.productGateway.findAll(aQuery).map(ProductListOutput::from);
    }
}
