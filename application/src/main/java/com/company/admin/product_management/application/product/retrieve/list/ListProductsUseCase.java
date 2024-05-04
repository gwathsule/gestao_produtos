package com.company.admin.product_management.application.product.retrieve.list;

import com.company.admin.product_management.application.UseCase;
import com.company.admin.product_management.domain.pagination.Pagination;
import com.company.admin.product_management.domain.product.ProductSearchQuery;

public abstract class ListProductsUseCase extends UseCase<ProductSearchQuery, Pagination<ProductListOutput>> {
}
