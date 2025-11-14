package com.algashop.ordering.core.domain.shoppingcart;

import com.algashop.ordering.core.domain.customer.CustomerId;
import com.algashop.ordering.core.domain.product.ProductId;

import java.time.OffsetDateTime;

public record ShoppingCartItemAddedEvent(
        ShoppingCartId shoppingCartId,
        CustomerId customerId,
        ProductId productId,
        OffsetDateTime addedAt
) {}
