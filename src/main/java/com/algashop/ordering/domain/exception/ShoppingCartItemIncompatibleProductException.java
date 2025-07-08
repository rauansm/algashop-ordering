package com.algashop.ordering.domain.exception;

import com.algashop.ordering.domain.valueobject.id.ProductId;
import com.algashop.ordering.domain.valueobject.id.ShoppingCartItemId;

public class ShoppingCartItemIncompatibleProductException extends DomainException {

    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }
}
