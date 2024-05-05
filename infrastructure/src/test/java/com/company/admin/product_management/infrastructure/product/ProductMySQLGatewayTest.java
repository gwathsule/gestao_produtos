package com.company.admin.product_management.infrastructure.product;

import com.company.admin.product_management.infrastructure.MySQLGatewayTest;
import com.company.admin.product_management.infrastructure.product.persistence.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class ProductMySQLGatewayTest {

    @Autowired
    private ProductMySQLGateway productGateway;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInjectedDependencies() {
        Assertions.assertNotNull(productGateway);
        Assertions.assertNotNull(productRepository);
    }
}
