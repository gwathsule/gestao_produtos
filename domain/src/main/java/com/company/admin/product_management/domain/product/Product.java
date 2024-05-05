package com.company.admin.product_management.domain.product;

import com.company.admin.product_management.domain.AggregateRoot;
import com.company.admin.product_management.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Product extends AggregateRoot<ProductID> {

    private Long code;
    private String description;
    private Instant fabricatedAt;
    private Instant expiredAt;
    private String supplierCode;
    private String supplierDescription;
    private String supplierCNPJ;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Product(
            final ProductID anID,
            final Long aCode,
            final String aDescription,
            final Instant aFabricatedAt,
            final Instant anExpiredAt,
            final String anSupplierCode,
            final String aSupplierDescription,
            final String aSupplierCNPJ,
            final boolean isActive,
            final Instant aCreatedAt,
            final Instant anUpdatedAt,
            final Instant aDeletedAt
    ) {
        super(anID);
        this.code = aCode;
        this.description = aDescription;
        this.fabricatedAt = aFabricatedAt;
        this.expiredAt = anExpiredAt;
        this.supplierCode = anSupplierCode;
        this.supplierDescription = aSupplierDescription;
        this.supplierCNPJ = aSupplierCNPJ;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = anUpdatedAt;
        this.deletedAt = aDeletedAt;
    }

    private Product(
            final ProductID anID,
            final String aDescription,
            final Instant aFabricatedAt,
            final Instant anExpiredAt,
            final String anSupplierCode,
            final String aSupplierDescription,
            final String aSupplierCNPJ,
            final boolean isActive,
            final Instant aCreatedAt,
            final Instant anUpdatedAt,
            final Instant aDeletedAt
    ) {
        super(anID);
        this.description = aDescription;
        this.fabricatedAt = aFabricatedAt;
        this.expiredAt = anExpiredAt;
        this.supplierCode = anSupplierCode;
        this.supplierDescription = aSupplierDescription;
        this.supplierCNPJ = aSupplierCNPJ;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreatedAt);
        this.updatedAt = Objects.requireNonNull(anUpdatedAt);
        this.deletedAt = aDeletedAt;
    }

    public static Product newProduct(
            final Long aCode,
            final String aDescription,
            final Instant aFabricatedAt,
            final Instant anExpiredAt,
            final String anSupplierCode,
            final String aSupplierDescription,
            final String aSupplierCNPJ,
            final boolean isActive
    ) {
        final var id = ProductID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Product(
                id,
                aCode,
                aDescription,
                aFabricatedAt,
                anExpiredAt,
                anSupplierCode,
                aSupplierDescription,
                aSupplierCNPJ,
                isActive,
                now,
                now,
                deletedAt
                );
    }

    public static Product newProduct(
            final String aDescription,
            final Instant aFabricatedAt,
            final Instant anExpiredAt,
            final String anSupplierCode,
            final String aSupplierDescription,
            final String aSupplierCNPJ,
            final boolean isActive
    ) {
        final var id = ProductID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Product(
                id,
                aDescription,
                aFabricatedAt,
                anExpiredAt,
                anSupplierCode,
                aSupplierDescription,
                aSupplierCNPJ,
                isActive,
                now,
                now,
                deletedAt
        );
    }

    public static Product with(final Product aProduct) {
        return with(
                aProduct.getId(),
                aProduct.code,
                aProduct.description,
                aProduct.fabricatedAt,
                aProduct.expiredAt,
                aProduct.supplierCode,
                aProduct.supplierDescription,
                aProduct.supplierCNPJ,
                aProduct.active,
                aProduct.createdAt,
                aProduct.updatedAt,
                aProduct.deletedAt
        );
    }

    public static Product with(
            final ProductID anID,
            final Long aCode,
            final String aDescription,
            final Instant aFabricatedAt,
            final Instant anExpiredAt,
            final String anSupplierCode,
            final String aSupplierDescription,
            final String aSupplierCNPJ,
            final boolean isActive,
            final Instant aCreatedAt,
            final Instant anUpdatedAt,
            final Instant aDeletedAt
    ) {
        return new Product(
                anID,
                aCode,
                aDescription,
                aFabricatedAt,
                anExpiredAt,
                anSupplierCode,
                aSupplierDescription,
                aSupplierCNPJ,
                isActive,
                aCreatedAt,
                anUpdatedAt,
                aDeletedAt
        );
    }

    public Product update(
            final Long aCode,
            final String aDescription,
            final Instant aFabricatedAt,
            final Instant anExpiredAt,
            final String anSupplierCode,
            final String aSupplierDescription,
            final String aSupplierCNPJ,
            final boolean isActive
    ) {
        if(isActive) {
            activate();
        } else {
            deactivate();
        }
        this.code = aCode;
        this.description = aDescription;
        this.fabricatedAt = aFabricatedAt;
        this.expiredAt = anExpiredAt;
        this.supplierCode = anSupplierCode;
        this.supplierDescription = aSupplierDescription;
        this.supplierCNPJ = aSupplierCNPJ;
        this.updatedAt = Instant.now();

        return this;
    }

    public Product activate(){
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Product deactivate(){
        if(getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ProductValidator(this, handler).validate();
    }

    public Long getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Instant getFabricatedAt() {
        return fabricatedAt;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public String getSupplierDescription() {
        return supplierDescription;
    }

    public String getSupplierCNPJ() {
        return supplierCNPJ;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
