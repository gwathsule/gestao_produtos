package com.company.admin.product_management.infrastructure.product;

import com.company.admin.product_management.domain.pagination.Pagination;
import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.product.ProductID;
import com.company.admin.product_management.domain.product.ProductSearchQuery;
import com.company.admin.product_management.infrastructure.product.persistence.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductMySQLGateway implements ProductGateway {

    private final ProductRepository repository;

    public ProductMySQLGateway(final ProductRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Product create(Product aProduct) {
        return null;
    }

    @Override
    public void deleteById(ProductID anId) {

    }

    @Override
    public Optional<Product> findById(ProductID anId) {
        return Optional.empty();
    }

    @Override
    public Product update(Product aProduct) {
        return null;
    }

    @Override
    public Pagination<Product> findAll(ProductSearchQuery aQuery) {
        return null;
    }
}
