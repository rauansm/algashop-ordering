package com.algashop.ordering.domain.shoppingcart;

import com.algashop.ordering.domain.DomainException;
import com.algashop.ordering.domain.ErrorMessages;

public class ShoppingCartDoesNotContainItemException extends DomainException {

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ShoppingCartItemId shoppingCartItemId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM, id, shoppingCartItemId));
    }
}