package com.company.admin.product_management.infrastructure.product.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record ProductApiOutput(
        @JsonProperty("id") String anId,
        @JsonProperty("code") String aCode,
        @JsonProperty("description") String aDescription,
        @JsonProperty("fabricated_at") Instant aFabricatedAt,
        @JsonProperty("expired_at") Instant anExpiredAt,
        @JsonProperty("supplier_code") String anSupplierCode,
        @JsonProperty("supplier_description") String aSupplierDescription,
        @JsonProperty("supplier_cnpj") String aSupplierCNPJ,
        @JsonProperty("is_active") Boolean isActive,
        @JsonProperty("created_at") Instant aCreatedAt,
        @JsonProperty("updated_at") Instant aUpdatedAt,
        @JsonProperty("deleted_at") Instant aDeletedAt
) {
}
