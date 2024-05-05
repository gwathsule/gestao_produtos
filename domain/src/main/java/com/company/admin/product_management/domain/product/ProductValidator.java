package com.company.admin.product_management.domain.product;

import com.company.admin.product_management.domain.validation.Error;
import com.company.admin.product_management.domain.validation.ValidationHandler;
import com.company.admin.product_management.domain.validation.Validator;

public class ProductValidator extends Validator {

    private final Product product;

    protected ProductValidator(final Product aProduct, final ValidationHandler aHandler) {
        super(aHandler);
        this.product = aProduct;
    }


    @Override
    public void validate() {
        checkDescriptionConstraints();
        checkExpiredAtConstraints();
        checkSupplierCNPJConstraints();
    }

    private void checkDescriptionConstraints() {
        final var description = this.product.getDescription();

        if(description == null) {
            this.validationHandler().append(new Error("'description' should not be null"));
        }
    }

    private void checkSupplierCNPJConstraints() {
        final var supplierCNPJ = this.product.getSupplierCNPJ();

        if(!supplierCNPJ.isEmpty() && supplierCNPJ.length() != 14) {
            this.validationHandler().append(new Error("'CNPJ' should be 14 characters"));
        }
    }

    public void checkExpiredAtConstraints() {
        final var expiredAt = this.product.getExpiredAt();

        if(expiredAt.isBefore(this.product.getFabricatedAt())) {
            this.validationHandler().append(new Error("'expiredAt' should not be before the fabricatedAt"));
        }
    }
 }
