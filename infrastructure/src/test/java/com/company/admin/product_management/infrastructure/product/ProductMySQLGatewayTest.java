package com.company.admin.product_management.infrastructure.product;

import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.infrastructure.MySQLGatewayTest;
import com.company.admin.product_management.infrastructure.product.persistence.ProductJpaEntity;
import com.company.admin.product_management.infrastructure.product.persistence.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@MySQLGatewayTest
public class ProductMySQLGatewayTest {

    @Autowired
    private ProductMySQLGateway productGateway;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void givenAValidProduct_whenCallsCreate_thenShouldReturnANewProduct()
    {
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;

        final var aProduct = Product.newProduct(
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        Assertions.assertEquals(0, productRepository.count());
        final var actualProduct = productGateway.create(aProduct);
        Assertions.assertEquals(1, productRepository.count());

        Assertions.assertEquals(expectedDescription, actualProduct.getDescription());
        Assertions.assertEquals(expectedFabricatedAt, actualProduct.getFabricatedAt());
        Assertions.assertEquals(expectedExpiredAt, actualProduct.getExpiredAt());
        Assertions.assertEquals(expectedSupplierCode, actualProduct.getSupplierCode());
        Assertions.assertEquals(expectedSupplierDescription, actualProduct.getSupplierDescription());
        Assertions.assertEquals(expectedSupplierCNPJ, actualProduct.getSupplierCNPJ());
        Assertions.assertEquals(expectedIsActive, actualProduct.isActive());
        Assertions.assertEquals(aProduct.getCreatedAt(), actualProduct.getCreatedAt());
        Assertions.assertEquals(aProduct.getUpdatedAt(), actualProduct.getUpdatedAt());
        Assertions.assertNull(actualProduct.getDeletedAt());

        Assertions.assertEquals(aProduct.getId(), actualProduct.getId());
        Assertions.assertNotNull(actualProduct.getCode());

        final var actualEntity = productRepository.findById(actualProduct.getCode()).get();

        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedFabricatedAt, actualEntity.getFabricatedAt());
        Assertions.assertEquals(expectedExpiredAt, actualEntity.getExpiredAt());
        Assertions.assertEquals(expectedSupplierCode, actualEntity.getSupplierCode());
        Assertions.assertEquals(expectedSupplierDescription, actualEntity.getSupplierDescription());
        Assertions.assertEquals(expectedSupplierCNPJ, actualEntity.getSupplierCNPJ());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aProduct.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aProduct.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());

        Assertions.assertEquals(aProduct.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(actualProduct.getCode(), actualEntity.getCode());

    }

    @Test
    public void givenAValidProduct_whenCallsUpdate_thenShouldReturnAUpdatedProduct()
    {
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;

        final var aProduct = Product.newProduct(
                "A normal product description.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "supplier-code",
                "A normal supplier description.",
                "59456277000176",
                true
        );

        Assertions.assertEquals(0, productRepository.count());

        final var outdatedProduct =  productRepository.saveAndFlush(ProductJpaEntity.from(aProduct));

        Assertions.assertEquals(aProduct.getDescription(), outdatedProduct.getDescription());
        Assertions.assertEquals(aProduct.getFabricatedAt(), outdatedProduct.getFabricatedAt());
        Assertions.assertEquals(aProduct.getExpiredAt(), outdatedProduct.getExpiredAt());
        Assertions.assertEquals(aProduct.getSupplierCode(), outdatedProduct.getSupplierCode());
        Assertions.assertEquals(aProduct.getSupplierDescription(), outdatedProduct.getSupplierDescription());

        Assertions.assertEquals(1, productRepository.count());

        final var aUpdatedProduct = Product.with(outdatedProduct.toAggregate()).update(
                outdatedProduct.getCode(),
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        final var actualProduct = productGateway.update(aUpdatedProduct);

        Assertions.assertEquals(1, productRepository.count());

        Assertions.assertEquals(expectedDescription, actualProduct.getDescription());
        Assertions.assertEquals(expectedFabricatedAt, actualProduct.getFabricatedAt());
        Assertions.assertEquals(expectedExpiredAt, actualProduct.getExpiredAt());
        Assertions.assertEquals(expectedSupplierCode, actualProduct.getSupplierCode());
        Assertions.assertEquals(expectedSupplierDescription, actualProduct.getSupplierDescription());
        Assertions.assertEquals(expectedSupplierCNPJ, actualProduct.getSupplierCNPJ());
        Assertions.assertEquals(expectedIsActive, actualProduct.isActive());
        Assertions.assertEquals(aProduct.getCreatedAt(), actualProduct.getCreatedAt());
        Assertions.assertTrue(aProduct.getUpdatedAt().isBefore(actualProduct.getUpdatedAt()));
        Assertions.assertNull(actualProduct.getDeletedAt());

        Assertions.assertEquals(aProduct.getId(), actualProduct.getId());
        Assertions.assertNotNull(actualProduct.getCode());

        final var actualEntity = productRepository.findById(actualProduct.getCode()).get();

        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedFabricatedAt, actualEntity.getFabricatedAt());
        Assertions.assertEquals(expectedExpiredAt, actualEntity.getExpiredAt());
        Assertions.assertEquals(expectedSupplierCode, actualEntity.getSupplierCode());
        Assertions.assertEquals(expectedSupplierDescription, actualEntity.getSupplierDescription());
        Assertions.assertEquals(expectedSupplierCNPJ, actualEntity.getSupplierCNPJ());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aProduct.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertTrue(aProduct.getUpdatedAt().isBefore(actualEntity.getUpdatedAt()));
        Assertions.assertNull(actualEntity.getDeletedAt());

        Assertions.assertEquals(aProduct.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(actualProduct.getCode(), actualEntity.getCode());
    }
}
