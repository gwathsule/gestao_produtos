package com.company.admin.product_management.infrastructure.product;

import com.company.admin.product_management.domain.pagination.Pagination;
import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.product.ProductSearchQuery;
import com.company.admin.product_management.infrastructure.product.persistence.ProductJpaEntity;
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
    public Product create( final Product aProduct) {
        return save(aProduct);
    }

    @Override
    public void deleteByCode(Long code) {
        if(this.repository.existsById(code)) {
            this.repository.deleteById(code);
        }
    }

    @Override
    public Optional<Product> findByCode(Long anCode) {
        return this.repository.findById(anCode)
                .map(ProductJpaEntity::toAggregate);
    }

    @Override
    public Product update(Product aProduct) {
        return save(aProduct);
    }

    @Override
    public Pagination<Product> findAll(ProductSearchQuery aQuery) {
        return null;
    }

    private Product save(final Product aProduct) {
        return this.repository.save(ProductJpaEntity.from(aProduct)).toAggregate();
    }
}
