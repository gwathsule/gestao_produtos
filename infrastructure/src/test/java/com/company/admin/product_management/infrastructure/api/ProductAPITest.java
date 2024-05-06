package com.company.admin.product_management.infrastructure.api;

import com.company.admin.product_management.application.product.create.CreateProductOutput;
import com.company.admin.product_management.application.product.create.CreateProductUseCase;
import com.company.admin.product_management.application.product.retrieve.get.GetProductByCodeUseCase;
import com.company.admin.product_management.application.product.retrieve.get.ProductOutput;
import com.company.admin.product_management.application.product.update.UpdateProductOutput;
import com.company.admin.product_management.application.product.update.UpdateProductUseCase;
import com.company.admin.product_management.domain.exceptions.DomainException;
import com.company.admin.product_management.domain.exceptions.NotFoundException;
import com.company.admin.product_management.domain.product.Product;
import com.company.admin.product_management.domain.validation.Error;
import com.company.admin.product_management.domain.validation.handler.Notification;
import com.company.admin.product_management.infrastructure.ControllerTest;
import com.company.admin.product_management.infrastructure.product.models.CreateProductApiInput;
import com.company.admin.product_management.infrastructure.product.models.UpdateProductApiInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static io.vavr.API.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = ProductAPI.class)
public class ProductAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateProductUseCase createProductUseCase;

    @MockBean
    private GetProductByCodeUseCase getProductByCodeUseCase;

    @MockBean
    private UpdateProductUseCase updateProductUseCase;

    @Test
    public void givenAValidInput_whenCallCreateProductApi_thenReturnCreatedProduct() throws Exception{
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;

        final var aInput = new CreateProductApiInput(
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        when(createProductUseCase.execute(any()))
                .thenReturn(Right(CreateProductOutput.from(123L)));

        final var request = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isCreated(),
                        MockMvcResultMatchers.header().string("Location", "/products/123")
                );

        verify(createProductUseCase, times(1)).execute(argThat(cmd ->
                        Objects.equals(expectedDescription, cmd.aDescription())
                                && Objects.equals(expectedFabricatedAt, cmd.aFabricatedAt())
                                && Objects.equals(expectedExpiredAt, cmd.anExpiredAt())
                                && Objects.equals(expectedSupplierCode, cmd.anSupplierCode())
                                && Objects.equals(expectedSupplierDescription, cmd.aSupplierDescription())
                                && Objects.equals(expectedSupplierCNPJ, cmd.aSupplierCNPJ())
                                && Objects.equals(expectedIsActive, cmd.isActive())
                ));
    }

    @Test
    public void givenInvalidDescription_whenCallCreateProductApi_thenReturnNotification() throws Exception{
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'description' should not be null";

        final var aInput = new CreateProductApiInput(
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        when(createProductUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedErrorMessage))));

        final var request = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().isUnprocessableEntity())
                        .andExpect(header().string("Location", Matchers.nullValue()))
                        .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(jsonPath("$.errors", hasSize(1)))
                        .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createProductUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedDescription, cmd.aDescription())
                        && Objects.equals(expectedFabricatedAt, cmd.aFabricatedAt())
                        && Objects.equals(expectedExpiredAt, cmd.anExpiredAt())
                        && Objects.equals(expectedSupplierCode, cmd.anSupplierCode())
                        && Objects.equals(expectedSupplierDescription, cmd.aSupplierDescription())
                        && Objects.equals(expectedSupplierCNPJ, cmd.aSupplierCNPJ())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenInvalidDescription_whenCallCreateProductApi_thenReturnDomainException() throws Exception{
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'description' should not be null";

        final var aInput = new CreateProductApiInput(
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        when(createProductUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedErrorMessage)));

        final var request = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo("Errors were found")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createProductUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedDescription, cmd.aDescription())
                        && Objects.equals(expectedFabricatedAt, cmd.aFabricatedAt())
                        && Objects.equals(expectedExpiredAt, cmd.anExpiredAt())
                        && Objects.equals(expectedSupplierCode, cmd.anSupplierCode())
                        && Objects.equals(expectedSupplierDescription, cmd.aSupplierDescription())
                        && Objects.equals(expectedSupplierCNPJ, cmd.aSupplierCNPJ())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAValidProduct_whenCallsGetProductByCodeApi_thenShouldReturnProduct() throws Exception{
        final var expectedDescription = "A normal product description.";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;
        final Long expectedCode = 123L;

        final var aProduct = Product.newProduct(
                expectedCode,
                expectedDescription,
                expectedFabricatedAt,
                expectedExpiredAt,
                expectedSupplierCode,
                expectedSupplierDescription,
                expectedSupplierCNPJ,
                expectedIsActive
        );

        when(getProductByCodeUseCase.execute(any())).thenReturn(ProductOutput.from(aProduct));

        final var request = MockMvcRequestBuilders
                .get("/products/code/{code}", expectedCode.toString())
                .contentType(MediaType.APPLICATION_JSON);
        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.print());

        response.andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", equalTo(aProduct.getId().getValue())))
                        .andExpect(jsonPath("$.description", equalTo(expectedDescription)))
                        .andExpect(jsonPath("$.fabricated_at", equalTo(expectedFabricatedAt.toString())))
                        .andExpect(jsonPath("$.expired_at", equalTo(expectedExpiredAt.toString())))
                        .andExpect(jsonPath("$.supplier_code", equalTo(expectedSupplierCode)))
                        .andExpect(jsonPath("$.supplier_description", equalTo(expectedSupplierDescription)))
                        .andExpect(jsonPath("$.supplier_cnpj", equalTo(expectedSupplierCNPJ)))
                        .andExpect(jsonPath("$.is_active", equalTo(expectedIsActive)))
                        .andExpect(jsonPath("$.created_at", equalTo(aProduct.getCreatedAt().toString())))
                        .andExpect(jsonPath("$.updated_at", equalTo(aProduct.getUpdatedAt().toString())))
                        .andExpect(jsonPath("$.deleted_at", equalTo(aProduct.getDeletedAt())))
                        .andExpect(jsonPath("$.code", equalTo(expectedCode.toString())));
        verify(getProductByCodeUseCase, times(1)).execute(eq(expectedCode));
    }

    @Test
    public void givenAInvalidProductCode_whenCallsGetProductByCodeApi_thenShouldNotFound() throws Exception{
        final var expectedErrorMessage = "Product with code 123 was not found";
        final Long expectedCode = 123L;

        when(getProductByCodeUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Product.class, expectedCode));

        final var request = MockMvcRequestBuilders.get("/products/code/{code}", expectedCode.toString());
        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

    }

    @Test
    public void givenAValidCommand_whenCallsUpdateProduct_shouldReturnProductId() throws Exception{

        final String expectedId = "1234";
        final Long expectedCode = 1234L;
        final var expectedDescription = "A updated normal product description.";
        final var expectedFabricatedAt = Instant.now().plus(5, ChronoUnit.DAYS);
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "updated-code";
        final var expectedSupplierDescription = "A updated normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000100";
        final var expectedIsActive = true;

        when(updateProductUseCase.execute(any()))
                .thenReturn(Right(UpdateProductOutput.from(expectedCode)));

        final var aInput = new UpdateProductApiInput(
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

        final var request = MockMvcRequestBuilders
                .put("/products/code/{code}", expectedCode.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.print());

        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(jsonPath("$.code", equalTo(expectedCode.intValue())));

        verify(updateProductUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedFabricatedAt, cmd.fabricatedAt())
                        && Objects.equals(expectedExpiredAt, cmd.expiredAt())
                        && Objects.equals(expectedSupplierCode, cmd.supplierCode())
                        && Objects.equals(expectedSupplierDescription, cmd.supplierDescription())
                        && Objects.equals(expectedSupplierCNPJ, cmd.supplierCNPJ())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAInvalidProductCode_whenCallsUpdateProductApi_thenShouldNotFound() throws Exception{
        final String expectedId = "1234";
        final Long expectedCode = 123L;
        final String expectedDescription = "description";
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Product with code 123 was not found";

        when(updateProductUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Product.class, expectedCode));

        final var aInput = new UpdateProductApiInput(
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

        final var request = MockMvcRequestBuilders
                .put("/products/code/{code}", expectedCode.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.print());

        response.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(updateProductUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedFabricatedAt, cmd.fabricatedAt())
                        && Objects.equals(expectedExpiredAt, cmd.expiredAt())
                        && Objects.equals(expectedSupplierCode, cmd.supplierCode())
                        && Objects.equals(expectedSupplierDescription, cmd.supplierDescription())
                        && Objects.equals(expectedSupplierCNPJ, cmd.supplierCNPJ())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAInvalidProductDescription_whenCallsUpdateProductApi_thenShouldThrowException() throws Exception{
        final String expectedId = "1234";
        final Long expectedCode = 123L;
        final String expectedDescription = null;
        final var expectedFabricatedAt = Instant.now();
        final var expectedExpiredAt = Instant.now().plus(50, ChronoUnit.DAYS);
        final var expectedSupplierCode = "supplier-code";
        final var expectedSupplierDescription = "A normal supplier description.";
        final var expectedSupplierCNPJ = "59456277000176";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'description' should not be null";

        when(updateProductUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedErrorMessage))));

        final var aInput = new UpdateProductApiInput(
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

        final var request = MockMvcRequestBuilders
                .put("/products/code/{code}", expectedCode.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.print());

        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(updateProductUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedFabricatedAt, cmd.fabricatedAt())
                        && Objects.equals(expectedExpiredAt, cmd.expiredAt())
                        && Objects.equals(expectedSupplierCode, cmd.supplierCode())
                        && Objects.equals(expectedSupplierDescription, cmd.supplierDescription())
                        && Objects.equals(expectedSupplierCNPJ, cmd.supplierCNPJ())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));

    }

}
