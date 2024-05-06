package com.company.admin.product_management.application.product.retrieve.get;

import com.company.admin.product_management.domain.exceptions.DomainException;
import com.company.admin.product_management.domain.exceptions.NotFoundException;
import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.product.ProductGateway;
import com.company.admin.product_management.domain.product.ProductID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetProductByCodeUseCaseTest {

    @InjectMocks
    private DefaultGetProductByCodeUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(productGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_thenShouldReturnCategory() {
        final var aProduct = Product.newProduct(
                4321L,
                "A normal product description.",
                Instant.now(),
                Instant.now().plus(10, ChronoUnit.DAYS),
                "supplier-code",
                "A normal supplier description.",
                "59456277000176",
                true
        );
        final var expectedCode = aProduct.getCode();

        when(productGateway.findByCode(eq(expectedCode)))
                .thenReturn(Optional.of(Product.with(aProduct)));

        final var actualProduct = useCase.execute(aProduct.getCode());

        Assertions.assertEquals(aProduct.getId(), actualProduct.id());
        Assertions.assertEquals(expectedCode, actualProduct.code());
        Assertions.assertEquals(aProduct.getDescription(), actualProduct.description());
        Assertions.assertEquals(aProduct.getFabricatedAt(), actualProduct.fabricatedAt());
        Assertions.assertEquals(aProduct.getExpiredAt(), actualProduct.expiredAt());
        Assertions.assertEquals(aProduct.getSupplierCode(), actualProduct.supplierCode());
        Assertions.assertEquals(aProduct.getSupplierDescription(), actualProduct.supplierDescription());
        Assertions.assertEquals(aProduct.getSupplierCNPJ(), actualProduct.supplierCNPJ());
        Assertions.assertEquals(aProduct.isActive(), actualProduct.isActive());
        Assertions.assertEquals(aProduct.getCreatedAt(), actualProduct.createdAt());
        Assertions.assertEquals(aProduct.getUpdatedAt(), actualProduct.updatedAt());
        Assertions.assertEquals(aProduct.getDeletedAt(), actualProduct.deletedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsGetProduct_thenShouldReturnNotFound() {
        final var expectedErrorMessage = "Product with code 1234 was not found";
        final var expectedCode = 1234L;

        when(productGateway.findByCode(eq(expectedCode))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedCode)
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidID_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedCode = 123L;

        when(productGateway.findByCode(eq(expectedCode))).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedCode)
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
