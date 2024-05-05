package com.company.admin.product_management.infrastructure.product;

import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductSearchQuery;
import com.company.admin.product_management.infrastructure.MySQLGatewayTest;
import com.company.admin.product_management.infrastructure.product.persistence.ProductJpaEntity;
import com.company.admin.product_management.infrastructure.product.persistence.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

    @Test
    public void givenAPrePersistedProductAndValidProductCode_whenTryToDeleteIt_thenShouldDelete() {
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
        final var savedProduct = productRepository.saveAndFlush(ProductJpaEntity.from(aProduct));
        Assertions.assertEquals(1, productRepository.count());
        productGateway.deleteByCode(savedProduct.getCode());
        Assertions.assertEquals(0, productRepository.count());
    }

    @Test
    public void givenAInvalidProductCode_whenTryToDeleteIt_thenShouldNotThrownError() {
        Assertions.assertEquals(0, productRepository.count());
        productGateway.deleteByCode(123L);
        Assertions.assertEquals(0, productRepository.count());
    }

    @Test
    public void givenAPrePersistedProductAndValidProductCode_whenCallsFindByCode_thenShouldReturnProduct()
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
        final var savedProductCode = productRepository.saveAndFlush(ProductJpaEntity.from(aProduct)).getCode();
        Assertions.assertEquals(1, productRepository.count());

        final var actualProduct = productGateway.findByCode(savedProductCode).get();

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
    }

    @Test
    public void givenAValidCodeNotStored_whenCallsFindByCode_thenShouldReturnEmpty()
    {
        Assertions.assertEquals(0, productRepository.count());
        final var actualProduct = productGateway.findByCode(213L);
        Assertions.assertTrue(actualProduct.isEmpty());
    }

    @Test
    public void givenPrePersistedProducts_whenCallsFindAll_thenShouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 3;
        final var expectedTotal = 3;

        final var aProduct1 = Product.newProduct(
                "first product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "first-product.",
                "first product.",
                "11111111111111",
                true
        );

        final var aProduct2 = Product.newProduct(
                "second product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "second-product.",
                "second product.",
                "22222222222222",
                true
        );

        final var aProduct3 = Product.newProduct(
                "third product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "third-product.",
                "third product.",
                "33333333333333",
                true
        );

        Assertions.assertEquals(0, productRepository.count());

        productRepository.saveAll(List.of(
                ProductJpaEntity.from(aProduct1),
                ProductJpaEntity.from(aProduct2),
                ProductJpaEntity.from(aProduct3)
        ));

        Assertions.assertEquals(3, productRepository.count());

        final var query = new ProductSearchQuery(0, 3, "", "code", "asc");

        final var actualResult = productGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(aProduct1.getId(), actualResult.items().get(0).getId());
        Assertions.assertEquals(aProduct2.getId(), actualResult.items().get(1).getId());
        Assertions.assertEquals(aProduct3.getId(), actualResult.items().get(2).getId());
    }

    @Test
    public void givenEmptyProductsTable_whenCallsFindAll_thenShouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        Assertions.assertEquals(0, productRepository.count());

        final var query = new ProductSearchQuery(0, 1, "", "code", "asc");

        final var actualResult = productGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(0, actualResult.items().size());
    }

    @Test
    public void givenFollowPagination_whenCallsFindAllWithPage1_thenShouldReturnPaginated() {
        var expectedPage = 0;
        var expectedPerPage = 1;
        var expectedTotal = 3;

        final var aProduct1 = Product.newProduct(
                "first product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "first-product.",
                "first product.",
                "11111111111111",
                true
        );

        final var aProduct2 = Product.newProduct(
                "second product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "second-product.",
                "second product.",
                "22222222222222",
                true
        );

        final var aProduct3 = Product.newProduct(
                "third product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "third-product.",
                "third product.",
                "33333333333333",
                true
        );

        Assertions.assertEquals(0, productRepository.count());

        productRepository.saveAll(List.of(
                ProductJpaEntity.from(aProduct1),
                ProductJpaEntity.from(aProduct2),
                ProductJpaEntity.from(aProduct3)
        ));

        Assertions.assertEquals(3, productRepository.count());


        var query = new ProductSearchQuery(0, 1, "", "code", "asc");
        var actualResult = productGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(aProduct1.getId(), actualResult.items().get(0).getId());

        //page 1
        query = new ProductSearchQuery(1, 1, "", "code", "asc");
        actualResult = productGateway.findAll(query);
        expectedPage = 1;

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(aProduct2.getId(), actualResult.items().get(0).getId());

        //page 1
        query = new ProductSearchQuery(2, 1, "", "code", "asc");
        actualResult = productGateway.findAll(query);
        expectedPage = 2;

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(aProduct3.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedProducts_whenCallsFindAllWithDescriptionMatches_thenShouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var aProduct1 = Product.newProduct(
                "first product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "first-product.",
                "first product.",
                "11111111111111",
                true
        );

        final var aProduct2 = Product.newProduct(
                "second product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "second-product.",
                "second product.",
                "22222222222222",
                true
        );

        final var aProduct3 = Product.newProduct(
                "third product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "third-product.",
                "third product.",
                "33333333333333",
                true
        );

        Assertions.assertEquals(0, productRepository.count());

        productRepository.saveAll(List.of(
                ProductJpaEntity.from(aProduct1),
                ProductJpaEntity.from(aProduct2),
                ProductJpaEntity.from(aProduct3)
        ));

        Assertions.assertEquals(3, productRepository.count());

        final var query = new ProductSearchQuery(0, 1, "sec", "code", "asc");

        final var actualResult = productGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(aProduct2.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedProducts_whenCallsFindAllWithSupplierMatches_thenShouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 2;

        final var aProduct1 = Product.newProduct(
                "first product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "first-product.",
                "first product supplier.",
                "11111111111111",
                true
        );

        final var aProduct2 = Product.newProduct(
                "second product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "second-product.",
                "second product.",
                "22222222222222",
                true
        );

        final var aProduct3 = Product.newProduct(
                "third product.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "third-product.",
                "third product supplier.",
                "33333333333333",
                true
        );

        Assertions.assertEquals(0, productRepository.count());

        productRepository.saveAll(List.of(
                ProductJpaEntity.from(aProduct1),
                ProductJpaEntity.from(aProduct2),
                ProductJpaEntity.from(aProduct3)
        ));

        Assertions.assertEquals(3, productRepository.count());

        final var query = new ProductSearchQuery(0, expectedPerPage, "SUPPLIER", "code", "asc");

        final var actualResult = productGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedTotal, actualResult.items().size());
        Assertions.assertEquals(aProduct1.getId(), actualResult.items().get(0).getId());
        Assertions.assertEquals(aProduct3.getId(), actualResult.items().get(1).getId());
    }
}
