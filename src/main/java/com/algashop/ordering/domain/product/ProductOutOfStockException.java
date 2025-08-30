package com.algashop.ordering.domain.product;

import com.algashop.ordering.domain.DomainException;
import com.algashop.ordering.domain.ErrorMessages;

public class ProductOutOfStockException extends DomainException {

    public ProductOutOfStockException(ProductId id) {
        super(String.format(ErrorMessages.ERROR_PRODUCT_IS_OUT_OF_STOCK, id));
    }
}