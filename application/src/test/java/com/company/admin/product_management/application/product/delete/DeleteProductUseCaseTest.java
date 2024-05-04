package com.company.admin.product_management.application.product.delete;

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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteProductUseCaseTest {

    @InjectMocks
    private DefaultDeleteProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(productGateway);
    }

    @Test
    public void givenAValidID_whenCallsDeleteProduct_shouldBeOk() {
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

        doNothing().when(productGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Mockito.verify(productGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAInvalidID_whenCallsDeleteProduct_shouldBeOk() {
        final var expectedId = ProductID.from("123");

        doNothing().when(productGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Mockito.verify(productGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidID_whenGatewayThrowsException_shouldReturnException() {
        final var expectedId = ProductID.from("123");

        doThrow(new IllegalStateException("Gateway Error")).when(productGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));
        Mockito.verify(productGateway, times(1)).deleteById(eq(expectedId));
    }
}
