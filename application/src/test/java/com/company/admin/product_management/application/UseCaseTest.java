package com.company.admin.product_management.application;

import com.company.admin.product_management.application.product.create.CreateProductCommand;
import com.company.admin.product_management.application.product.create.DefaultCreateProductUseCase;
import com.company.admin.product_management.domain.exceptions.DomainException;
import com.company.admin.product_management.domain.product.ProductGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UseCaseTest {

    @InjectMocks
    private DefaultCreateProductUseCase useCase;

    @Mock
    private ProductGateway gateway;

    @Test
    public void givenAValidCommand_whenCallsCreateProduct_shouldReturnProductId() {
        final var expectedCode = 1234L;
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;

        final var aCommand = CreateProductCommand.with(
                expectedCode,
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        when(gateway.create(any())).thenAnswer(returnsFirstArg());
        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(gateway, times(1)).create(argThat(aProduct ->
                        Objects.equals(expectedCode, aProduct.getCode())
                        && Objects.equals(expectedDescription, aProduct.getDescription())
                        && Objects.equals(expectedFabricatedAt, aProduct.getFabricatedAt())
                        && Objects.equals(expectedExpiredAt, aProduct.getExpiredAt())
                        && Objects.equals(expectedSupplierCode, aProduct.getSupplierCode())
                        && Objects.equals(expectedSupplierDescription, aProduct.getSupplierDescription())
                        && Objects.equals(expectedSupplierCNPJ, aProduct.getSupplierCNPJ())
                        && Objects.equals(expectedIsActive, aProduct.isActive())
                        && Objects.nonNull(aProduct.getId())
                        && Objects.nonNull(aProduct.getCreatedAt())
                        && Objects.nonNull(aProduct.getUpdatedAt())
                        && Objects.isNull(aProduct.getDeletedAt())
        ));
    }

    @Test
    public void givenSomeInvalidData_whenCallsCreateProduct_shouldReturnReturnException() {
        final var code = 1234L;
        final var description = "A normal product description.";
        final var invalidFabricatedAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var invalidExpiredAt = Instant.now();
        final var supplierCode = "supplier-code";
        final var supplierDescription = "A normal supplier description.";
        final var supplierCNPJ = "59456277000176";
        final var isActive = true;
        final var expectedErrorMessage = "'expiredAt' should not be before the fabricatedAt";
        final var expectedErrorCount = 1;


        final var aCommand = CreateProductCommand.with(
                code,
                description,
                invalidFabricatedAt,
                invalidExpiredAt,
                supplierCode,
                supplierDescription,
                supplierCNPJ,
                isActive
        );

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Mockito.verify(gateway, times(0)).create(any());
    }
}
