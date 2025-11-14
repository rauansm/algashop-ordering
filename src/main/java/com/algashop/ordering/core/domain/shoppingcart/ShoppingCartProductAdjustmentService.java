package com.algashop.ordering.core.domain.shoppingcart;

import com.algashop.ordering.core.domain.commons.Money;
import com.algashop.ordering.core.domain.product.ProductId;

public interface ShoppingCartProductAdjustmentService {
    void adjustPrice(ProductId productId, Money updatedPrice);
    void changeAvailability(ProductId productId, boolean available);
}
