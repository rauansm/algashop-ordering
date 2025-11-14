package com.algashop.ordering.core.domain.shoppingcart;

import com.algashop.ordering.core.domain.customer.CustomerId;

import java.time.OffsetDateTime;

public record ShoppingCartCreatedEvent(
        ShoppingCartId shoppingCartId,
        CustomerId customerId,
        OffsetDateTime createdAt
) {}
