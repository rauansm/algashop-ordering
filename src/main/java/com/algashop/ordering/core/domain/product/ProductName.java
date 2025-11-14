package com.algashop.ordering.core.domain.product;

import com.algashop.ordering.core.domain.FieldValidations;

public record ProductName(String value) {

    public ProductName {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
