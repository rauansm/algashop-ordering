package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.customer.CustomerId;

import java.time.OffsetDateTime;

public record OrderPaidEvent(OrderId orderId, CustomerId customerId, OffsetDateTime paidAt) {
}
