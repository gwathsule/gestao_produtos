package com.company.admin.product_management.application.product.delete;

import com.company.admin.product_management.application.UnitUseCase;

public abstract class DeleteProductUseCase extends UnitUseCase<Long> {
    public abstract void execute(Long aCode);
}
