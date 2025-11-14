package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.customer.CustomerId;

import java.time.OffsetDateTime;

public record OrderReadyEvent(OrderId orderId, CustomerId customerId, OffsetDateTime readyAt){
}
