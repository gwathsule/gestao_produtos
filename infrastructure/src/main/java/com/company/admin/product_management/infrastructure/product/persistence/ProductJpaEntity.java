package com.company.admin.product_management.infrastructure.product.persistence;

import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    private String id;

    @Column(name = "code", nullable = false)
    private Long code;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "fabricated_at", columnDefinition = "DATETIME(6)")
    private Instant fabricatedAt;

    @Column(name = "expired_at", columnDefinition = "DATETIME(6)")
    private Instant expiredAt;

    @Column(name = "supplier_code")
    private String supplierCode;

    @Column(name = "supplier_description")
    private String supplierDescription;

    @Column(name = "supplier_cnpj", length = 14)
    private String supplierCNPJ;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    public ProductJpaEntity() {}

    public static ProductJpaEntity from(final Product aProduct) {
        return new ProductJpaEntity(
                aProduct.getId().getValue(),
                aProduct.getCode(),
                aProduct.getDescription(),
                aProduct.getFabricatedAt(),
                aProduct.getExpiredAt(),
                aProduct.getSupplierCode(),
                aProduct.getSupplierDescription(),
                aProduct.getSupplierCNPJ(),
                aProduct.isActive(),
                aProduct.getCreatedAt(),
                aProduct.getUpdatedAt(),
                aProduct.getDeletedAt()
        );
    }

    public Product toAggregate() {
        return Product.with(
                ProductID.from(getId()),
                getCode(),
                getDescription(),
                getFabricatedAt(),
                getExpiredAt(),
                getSupplierCode(),
                getSupplierDescription(),
                getSupplierCNPJ(),
                isActive(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    private ProductJpaEntity(
            String id,
            Long code,
            String description,
            Instant fabricatedAt,
            Instant expiredAt,
            String supplierCode,
            String supplierDescription,
            String supplierCNPJ,
            boolean active,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.fabricatedAt = fabricatedAt;
        this.expiredAt = expiredAt;
        this.supplierCode = supplierCode;
        this.supplierDescription = supplierDescription;
        this.supplierCNPJ = supplierCNPJ;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getFabricatedAt() {
        return fabricatedAt;
    }

    public void setFabricatedAt(Instant fabricatedAt) {
        this.fabricatedAt = fabricatedAt;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierDescription() {
        return supplierDescription;
    }

    public void setSupplierDescription(String supplierDescription) {
        this.supplierDescription = supplierDescription;
    }

    public String getSupplierCNPJ() {
        return supplierCNPJ;
    }

    public void setSupplierCNPJ(String supplierCNPJ) {
        this.supplierCNPJ = supplierCNPJ;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}
