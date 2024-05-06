package com.company.admin.product_management.infrastructure.api;

import com.company.admin.product_management.application.product.create.CreateProductOutput;
import com.company.admin.product_management.application.product.create.CreateProductUseCase;
import com.company.admin.product_management.domain.exceptions.DomainException;
import com.company.admin.product_management.domain.validation.Error;
import com.company.admin.product_management.domain.validation.handler.Notification;
import com.company.admin.product_management.infrastructure.ControllerTest;
import com.company.admin.product_management.infrastructure.product.models.CreateProductApiInput;
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

import static io.vavr.API.Left;
import static io.vavr.API.Right;
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
}
