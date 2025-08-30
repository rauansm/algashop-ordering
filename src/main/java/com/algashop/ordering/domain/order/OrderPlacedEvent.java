package com.algashop.ordering.domain.order;

import com.algashop.ordering.domain.customer.CustomerId;

import java.time.OffsetDateTime;

public record OrderPlacedEvent(OrderId orderId, CustomerId customerId, OffsetDateTime placedAt) {
}
