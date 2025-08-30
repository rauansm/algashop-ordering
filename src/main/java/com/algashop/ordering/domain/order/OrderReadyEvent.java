package com.algashop.ordering.domain.order;

import com.algashop.ordering.domain.customer.CustomerId;

import java.time.OffsetDateTime;

public record OrderReadyEvent(OrderId orderId, CustomerId customerId, OffsetDateTime readyAt){
}
