package com.company.admin.product_management.application.product.update;

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
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateProductUseCaseTest {

    @InjectMocks
    private DefaultUpdateProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(productGateway);
    }

    // 2. Teste passando uma propriedade inválida (name) TODO
    // 3. Teste atualizando um produto para inativa TODO
    // 4. Teste simulando um erro generico vindo do gateway TODO

    @Test
    public void givenAValidCommand_whenCallsUpdateProduct_shouldReturnProductId() {

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
        final var expectedCode = 1234L;
        final var expectedDescription = "A updated normal product description.";
        final var expectedFabricatedAt = Instant.now().plus(5, ChronoUnit.DAYS);
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "updated-code";
        final var expectedSupplierDescription = "A updated normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000100";
        final var expectedIsActive = true;


        final var aCommand = UpdateProductCommand.with(
                expectedId.getValue(),
                expectedCode,
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        when(productGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Product.with(aProduct)));

        when(productGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(productGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(productGateway, times(1)).update(argThat(
                aUpdatedProduct ->
                        Objects.equals(expectedCode, aUpdatedProduct.getCode())
                                && Objects.equals(expectedDescription, aUpdatedProduct.getDescription())
                                && Objects.equals(expectedFabricatedAt, aUpdatedProduct.getFabricatedAt())
                                && Objects.equals(expectedExpiredAt, aUpdatedProduct.getExpiredAt())
                                && Objects.equals(expectedSupplierCode, aUpdatedProduct.getSupplierCode())
                                && Objects.equals(expectedSupplierDescription, aUpdatedProduct.getSupplierDescription())
                                && Objects.equals(expectedSupplierCNPJ, aUpdatedProduct.getSupplierCNPJ())
                                && Objects.equals(expectedIsActive, aUpdatedProduct.isActive())
                                && Objects.equals(expectedId, aUpdatedProduct.getId())
                                && Objects.equals(aProduct.getCreatedAt(), aUpdatedProduct.getCreatedAt())
                                && aProduct.getUpdatedAt().isBefore(aUpdatedProduct.getUpdatedAt())
                                && Objects.isNull(aUpdatedProduct.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalid_whenCallsUpdateProduct_shouldReturnNotFoundError()
    {
        final var expectedId = "1234";
        final var expectedCode = 1234L;
        final var expectedDescription = "A updated normal product description.";
        final var expectedFabricatedAt = Instant.now().plus(5, ChronoUnit.DAYS);
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "updated-code";
        final var expectedSupplierDescription = "A updated normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000100";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Product with ID 1234 was not found";
        final var expectedErrorCount = 1;


        final var aCommand = UpdateProductCommand.with(
                expectedId,
                expectedCode,
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        when(productGateway.findById(eq(ProductID.from(expectedId))))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(productGateway, times(1)).findById(eq(ProductID.from(expectedId)));

        Mockito.verify(productGateway, times(0)).update(any());

    }

}
