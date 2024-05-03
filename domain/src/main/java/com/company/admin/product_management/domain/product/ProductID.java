package com.company.admin.product_management.domain.product;

import com.company.admin.product_management.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ProductID extends Identifier {

    private final String value;

    public ProductID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static ProductID unique() {
        return ProductID.from(UUID.randomUUID());
    }

    public static ProductID from(final String anId) {
        return new ProductID(anId);
    }

    public static ProductID from(final UUID anId) {
        return new ProductID(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID that = (ProductID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
