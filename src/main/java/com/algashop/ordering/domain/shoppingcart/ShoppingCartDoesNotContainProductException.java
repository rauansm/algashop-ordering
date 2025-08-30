package com.algashop.ordering.domain.shoppingcart;

import com.algashop.ordering.domain.DomainException;
import com.algashop.ordering.domain.ErrorMessages;
import com.algashop.ordering.domain.product.ProductId;

public class ShoppingCartDoesNotContainProductException extends DomainException {

    public ShoppingCartDoesNotContainProductException(ShoppingCartId id, ProductId productId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT, id, productId));
    }
}
