package com.company.admin.product_management.application.product.delete;

import com.company.admin.product_management.domain.product.ProductGateway;

import java.util.Objects;

public class DefaultDeleteProductUseCase extends DeleteProductUseCase{

    private final ProductGateway productGateway;

    public DefaultDeleteProductUseCase(ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public void execute(Long aCode) {
        this.productGateway.deleteByCode(aCode);
    }
}
