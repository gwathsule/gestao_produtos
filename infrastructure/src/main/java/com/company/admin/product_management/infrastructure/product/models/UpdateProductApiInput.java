package com.company.admin.product_management.infrastructure.product.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record UpdateProductApiInput(
        @JsonProperty("id") String anId,
        @JsonProperty("code") Long aCode,
        @JsonProperty("description") String aDescription,
        @JsonProperty("fabricated_at") Instant aFabricatedAt,
        @JsonProperty("expired_at") Instant anExpiredAt,
        @JsonProperty("supplier_code") String anSupplierCode,
        @JsonProperty("supplier_description") String aSupplierDescription,
        @JsonProperty("supplier_cnpj") String aSupplierCNPJ,
        @JsonProperty("is_active") Boolean isActive
){
}
