package com.company.admin.product_management.domain.pagination;

import java.util.List;

public record Pagination<T>(
        int currentPage,
        int perPage,
        int total,
        List<T> items

) {
}
