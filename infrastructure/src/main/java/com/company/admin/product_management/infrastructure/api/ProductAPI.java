package com.company.admin.product_management.infrastructure.api;

import com.company.admin.product_management.domain.pagination.Pagination;
import com.company.admin.product_management.infrastructure.product.models.CreateProductRequest;
import com.company.admin.product_management.infrastructure.product.models.ProductListResponse;
import com.company.admin.product_management.infrastructure.product.models.ProductResponse;
import com.company.admin.product_management.infrastructure.product.models.UpdateProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "products")
@Tag(name = "Products")
public interface ProductAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "422", description = "Validation error"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    ResponseEntity<?> createProduct(@RequestBody CreateProductRequest input);

    @GetMapping
    @Operation(summary = "List paginated products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "422", description = "Validation error"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    Pagination<ProductListResponse> listProduct(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    @GetMapping(
            value = "{code}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get product by it's code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    ProductResponse getByCode(@PathVariable String code);

    @RequestMapping(value = "/code/{code}", method = RequestMethod.PUT)
    @PutMapping(
            value = "{code}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a product by it's code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    ResponseEntity<?> updateByCode(@PathVariable String code, @RequestBody UpdateProductRequest input);

    @RequestMapping(value = "/code/{code}", method = RequestMethod.DELETE)
    @DeleteMapping(
            value = "{code}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product by it's code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    void deleteByCode(@PathVariable String code);
}
