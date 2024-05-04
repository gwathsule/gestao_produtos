package com.company.admin.product_management.application.product.retrieve.get;

import com.company.admin.product_management.domain.exceptions.DomainException;
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
public class GetProductByIdUseCaseTest {

    @InjectMocks
    private DefaultGetProductByIdUseCase useCase;

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
        final var expectedId = aProduct.getId();

        when(productGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Product.with(aProduct)));//TODO clone nao funcionou

        final var actualProduct = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, actualProduct.id());
        Assertions.assertEquals(aProduct.getCode(), actualProduct.code());
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
        final var expectedErrorMessage = "Product with ID 1234 was not found";
        final var expectedId = ProductID.from("1234");

        when(productGateway.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> useCase.execute(expectedId.getValue())
        );
    }

    @Test
    public void givenAValidID_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = ProductID.from("123");

        when(productGateway.findById(eq(expectedId))).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
