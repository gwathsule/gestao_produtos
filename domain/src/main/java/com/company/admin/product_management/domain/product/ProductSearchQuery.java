package com.company.admin.product_management.domain.product;

public record ProductSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
