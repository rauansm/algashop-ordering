package com.algashop.ordering.domain.shoppingcart;

import com.algashop.ordering.domain.customer.CustomerId;
import com.algashop.ordering.domain.product.ProductId;

import java.time.OffsetDateTime;

public record ShoppingCartItemAddedEvent(
        ShoppingCartId shoppingCartId,
        CustomerId customerId,
        ProductId productId,
        OffsetDateTime addedAt
) {}
