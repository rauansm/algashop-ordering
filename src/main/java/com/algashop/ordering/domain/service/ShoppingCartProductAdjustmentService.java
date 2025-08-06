package com.algashop.ordering.domain.service;

import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.id.ProductId;

public interface ShoppingCartProductAdjustmentService {
    void adjustPrice(ProductId productId, Money updatedPrice);
    void changeAvailability(ProductId productId, boolean available);
}
