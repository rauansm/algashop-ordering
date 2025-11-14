package com.algashop.ordering.core.domain.shoppingcart;

import com.algashop.ordering.core.domain.DomainException;
import com.algashop.ordering.core.domain.ErrorMessages;
import com.algashop.ordering.core.domain.product.ProductId;

public class ShoppingCartItemIncompatibleProductException extends DomainException {

    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }
}
