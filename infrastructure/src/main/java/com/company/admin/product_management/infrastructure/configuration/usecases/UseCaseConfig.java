package com.company.admin.product_management.infrastructure.configuration.usecases;

import com.company.admin.product_management.application.product.create.CreateProductUseCase;
import com.company.admin.product_management.application.product.create.DefaultCreateProductUseCase;
import com.company.admin.product_management.application.product.delete.DefaultDeleteProductUseCase;
import com.company.admin.product_management.application.product.delete.DeleteProductUseCase;
import com.company.admin.product_management.application.product.retrieve.get.DefaultGetProductByCodeUseCase;
import com.company.admin.product_management.application.product.retrieve.get.GetProductByCodeUseCase;
import com.company.admin.product_management.application.product.retrieve.list.DefaultListProductsUseCase;
import com.company.admin.product_management.application.product.retrieve.list.ListProductsUseCase;
import com.company.admin.product_management.application.product.update.DefaultUpdateProductUseCase;
import com.company.admin.product_management.application.product.update.UpdateProductUseCase;
import com.company.admin.product_management.domain.product.ProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final ProductGateway productGateway;

    public UseCaseConfig(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new DefaultCreateProductUseCase(productGateway);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase() {
        return new DefaultDeleteProductUseCase(productGateway);
    }

    @Bean
    public GetProductByCodeUseCase getProductByCodeUseCase() {
        return new DefaultGetProductByCodeUseCase(productGateway);
    }

    @Bean
    public ListProductsUseCase listProductsUseCase() {
        return new DefaultListProductsUseCase(productGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new DefaultUpdateProductUseCase(productGateway);
    }
}
