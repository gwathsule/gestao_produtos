package com.company.admin.product_management.domain.product;

import com.company.admin.product_management.domain.exceptions.DomainException;
import com.company.admin.product_management.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ProductTest {

    @Test
    public void givenAValidaParams_whenCallNewProduct_thenInstantiateAProduct(){
        final var expectedCode = 1234L;
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;

        final var actualProduct = Product.newProduct(
                expectedCode,
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> actualProduct.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualProduct);
        Assertions.assertNotNull(actualProduct.getId());
        Assertions.assertEquals(expectedCode, actualProduct.getCode());
        Assertions.assertEquals(expectedDescription, actualProduct.getDescription());
        Assertions.assertEquals(expectedFabricatedAt, actualProduct.getFabricatedAt());
        Assertions.assertEquals(expectedExpiredAt, actualProduct.getExpiredAt());
        Assertions.assertEquals(expectedSupplierCode, actualProduct.getSupplierCode());
        Assertions.assertEquals(expectedSupplierDescription, actualProduct.getSupplierDescription());
        Assertions.assertEquals(expectedSupplierCNPJ, actualProduct.getSupplierCNPJ());
        Assertions.assertEquals(expectedIsActive, actualProduct.isActive());
        Assertions.assertNotNull(actualProduct.getCreatedAt());
        Assertions.assertNotNull(actualProduct.getUpdatedAt());
        Assertions.assertNull(actualProduct.getDeletedAt());
    }

    @Test
    public void givenAnInvalidDescription_whenCallNewProductAndValidate_thenShouldReceiveError(){
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' should not be null";
        final String invalidDescription = null;

        validateError(
                1234L,
                invalidDescription,
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "supplier-code",
                "A normal supplier description.",
                "59456277000176",
                true,
                expectedErrorCount,
                expectedErrorMessage
        );
    }

    @Test
    public void givenAnNullCode_whenCallNewProductAndValidate_thenShouldReceiveError(){
        final var expectedErrorCount = 1;
        final var expectedErrorMessage ="'code' should not be null";
        final Long invalidCode = null;

        validateError(
                invalidCode,
                "A normal product description.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "supplier-code",
                "A normal supplier description.",
                "59456277000176",
                true,
                expectedErrorCount,
                expectedErrorMessage
        );
    }

    @Test
    public void givenAnSupplierCNPJLengthOtherThan14_whenCallNewProductAndValidate_thenShouldReceiveError(){
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'CNPJ' should be 14 characters";
        final var invalidCNPJ = "12345";

        validateError(
                1234L,
                "A normal product description.",
                Instant.now(),
                Instant.now().plus(50, ChronoUnit.DAYS),
                "supplier-code",
                "A normal supplier description.",
                invalidCNPJ,
                true,
                expectedErrorCount,
                expectedErrorMessage
        );
    }

    @Test
    public void givenAnExpiredAtBeforeFabricatedAt_whenCallNewProductAndValidate_thenShouldReceiveError(){
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'expiredAt' should not be before the fabricatedAt";
        final Instant invalidExpiredAt = Instant.now();
        final Instant invalidFabricatedAt = Instant.now().plus(50, ChronoUnit.DAYS);

        validateError(
                1234L,
                "A normal product description.",
                invalidFabricatedAt,
                invalidExpiredAt,
                "supplier-code",
                "A normal supplier description.",
                "59456277000176",
                true,
                expectedErrorCount,
                expectedErrorMessage
        );
    }

    @Test
    public void givenAValidActiveProduct_whenCallDeactivate_thenReturnDeactivatedProduct()
    {
        final var expectedCode = 1234L;
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = false;

        final var validProduct = Product.newProduct(
                expectedCode,
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                true
        );

        Assertions.assertDoesNotThrow(() -> validProduct.validate(new ThrowsValidationHandler()));

        final var updatedAt = validProduct.getUpdatedAt();
        Assertions.assertTrue(validProduct.isActive());
        Assertions.assertNull(validProduct.getDeletedAt());

        final var actualProduct = validProduct.deactivate();

        Assertions.assertDoesNotThrow(() -> actualProduct.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedCode, actualProduct.getCode());
        Assertions.assertEquals(expectedDescription, actualProduct.getDescription());
        Assertions.assertEquals(expectedFabricatedAt, actualProduct.getFabricatedAt());
        Assertions.assertEquals(expectedExpiredAt, actualProduct.getExpiredAt());
        Assertions.assertEquals(expectedSupplierCode, actualProduct.getSupplierCode());
        Assertions.assertEquals(expectedSupplierDescription, actualProduct.getSupplierDescription());
        Assertions.assertEquals(expectedSupplierCNPJ, actualProduct.getSupplierCNPJ());
        Assertions.assertEquals(expectedIsActive, actualProduct.isActive());
        Assertions.assertNotNull(actualProduct.getCreatedAt());
        Assertions.assertTrue(actualProduct.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualProduct.getDeletedAt());
    }

    @Test
    public void givenAValidInactiveProduct_whenCallActivate_thenReturnActivatedProduct()
    {
        final var expectedCode = 1234L;
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;

        final var validProduct = Product.newProduct(
                expectedCode,
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                false
        );

        Assertions.assertDoesNotThrow(() -> validProduct.validate(new ThrowsValidationHandler()));

        final var updatedAt = validProduct.getUpdatedAt();
        Assertions.assertFalse(validProduct.isActive());
        Assertions.assertNotNull(validProduct.getDeletedAt());

        final var actualProduct = validProduct.activate();

        Assertions.assertDoesNotThrow(() -> actualProduct.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedCode, actualProduct.getCode());
        Assertions.assertEquals(expectedDescription, actualProduct.getDescription());
        Assertions.assertEquals(expectedFabricatedAt, actualProduct.getFabricatedAt());
        Assertions.assertEquals(expectedExpiredAt, actualProduct.getExpiredAt());
        Assertions.assertEquals(expectedSupplierCode, actualProduct.getSupplierCode());
        Assertions.assertEquals(expectedSupplierDescription, actualProduct.getSupplierDescription());
        Assertions.assertEquals(expectedSupplierCNPJ, actualProduct.getSupplierCNPJ());
        Assertions.assertEquals(expectedIsActive, actualProduct.isActive());
        Assertions.assertNotNull(actualProduct.getCreatedAt());
        Assertions.assertTrue(actualProduct.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualProduct.getDeletedAt());
    }

    @Test
    public void givenAValidProduct_whenCallUpdate_thenReturnUpdatedProduct()
    {
        final var expectedCode = 1234L;
        final var expectedDescription = "A updated normal product description.";
        final var expectedFabricatedAt = Instant.now().plus(5, ChronoUnit.DAYS);
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "updated-code";
        final var expectedSupplierDescription = "A updated normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000100";
        final var expectedIsActive = false;

        final var validProduct = Product.newProduct(
                4321L,
                "A normal product description.",
                Instant.now(),
                Instant.now().plus(10, ChronoUnit.DAYS),
                "supplier-code",
                "A normal supplier description.",
                "59456277000176",
                true
        );

        Assertions.assertDoesNotThrow(() -> validProduct.validate(new ThrowsValidationHandler()));

        final var updatedAt = validProduct.getUpdatedAt();

        final var updatedProduct = validProduct.update(
                expectedCode,
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        Assertions.assertDoesNotThrow(() -> updatedProduct.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedCode, updatedProduct.getCode());
        Assertions.assertEquals(expectedDescription, updatedProduct.getDescription());
        Assertions.assertEquals(expectedFabricatedAt, updatedProduct.getFabricatedAt());
        Assertions.assertEquals(expectedExpiredAt, updatedProduct.getExpiredAt());
        Assertions.assertEquals(expectedSupplierCode, updatedProduct.getSupplierCode());
        Assertions.assertEquals(expectedSupplierDescription, updatedProduct.getSupplierDescription());
        Assertions.assertEquals(expectedSupplierCNPJ, updatedProduct.getSupplierCNPJ());
        Assertions.assertEquals(expectedIsActive, updatedProduct.isActive());
        Assertions.assertNotNull(updatedProduct.getCreatedAt());
        Assertions.assertTrue(updatedProduct.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(updatedProduct.getDeletedAt());
    }

    private void validateError(
            final Long code,
            final String description,
            final Instant fabricatedAt,
            final Instant expiredAt,
            final String supplierCode,
            final String supplierDescription,
            final String supplierCNPJ,
            final boolean isActive,
            final Integer anExpectedErrorCount,
            final String anExpectedErrorMessage
    ){
        final var actualProduct = Product.newProduct(
                code,
                description,
                fabricatedAt,
                expiredAt,
                supplierCode,
                supplierDescription,
                supplierCNPJ,
                isActive
        );

        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> actualProduct.validate(new ThrowsValidationHandler())
        );

        Assertions.assertEquals(anExpectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(anExpectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
