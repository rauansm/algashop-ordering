package com.algashop.ordering.domain.shoppingcart;

import com.algashop.ordering.domain.commons.Money;
import com.algashop.ordering.domain.product.ProductId;

public interface ShoppingCartProductAdjustmentService {
    void adjustPrice(ProductId productId, Money updatedPrice);
    void changeAvailability(ProductId productId, boolean available);
}
