package com.algashop.ordering.core.ports.in.order;

import com.algashop.ordering.core.ports.out.orders.OrderDetailOutput;
import com.algashop.ordering.core.ports.out.orders.OrderSummaryOutput;
import org.springframework.data.domain.Page;

public interface ForQueryingOrders {
    OrderDetailOutput findById(String id);
    Page<OrderSummaryOutput> filter(OrderFilter filter);
}