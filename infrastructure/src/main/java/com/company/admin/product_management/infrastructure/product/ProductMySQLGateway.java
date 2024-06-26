package com.company.admin.product_management.infrastructure.product;

import com.company.admin.product_management.domain.pagination.Pagination;
import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.product.ProductSearchQuery;
import com.company.admin.product_management.infrastructure.product.persistence.ProductJpaEntity;
import com.company.admin.product_management.infrastructure.product.persistence.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.company.admin.product_management.infrastructure.utils.SpecificationUtils.like;

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
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        //dynamic search
        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str ->{
                    final Specification<ProductJpaEntity> descriptionLike = like("description", str);
                    final Specification<ProductJpaEntity> supplierCodeLike = like("supplierCode", str);
                    final Specification<ProductJpaEntity> supplierDescriptionLike = like("supplierDescription", str);
                    final Specification<ProductJpaEntity> supplierCNPJnLike = like("supplierCNPJ", str);
                    return descriptionLike.or(supplierCodeLike).or(supplierDescriptionLike).or(supplierCNPJnLike);
                }
                )
                .orElse(null);

        final var pageResult = this.repository.findAll(Specification.where(specifications), page);
        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                (int) pageResult.getTotalElements(),
                pageResult.map(ProductJpaEntity::toAggregate).toList()
        );
    }

    private Product save(final Product aProduct) {
        return this.repository.save(ProductJpaEntity.from(aProduct)).toAggregate();
    }
}
