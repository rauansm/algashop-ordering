package com.algashop.ordering.core.domain.product;

import com.algashop.ordering.core.domain.DomainException;
import com.algashop.ordering.core.domain.ErrorMessages;

public class ProductOutOfStockException extends DomainException {

    public ProductOutOfStockException(ProductId id) {
        super(String.format(ErrorMessages.ERROR_PRODUCT_IS_OUT_OF_STOCK, id));
    }
}