package com.company.admin.product_management.domain.product;

import com.company.admin.product_management.domain.pagination.Pagination;

import java.util.Optional;

public interface ProductGateway {

    Product create(Product aProduct);

    void deleteByCode(Long code);

    Optional<Product> findByCode(Long anCode);

    Product update(Product aProduct);

    Pagination<Product> findAll(ProductSearchQuery aQuery);
}
