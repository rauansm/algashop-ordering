package com.algashop.ordering.core.application.order;

import com.algashop.ordering.core.ports.out.orders.ForObtainingOrders;
import com.algashop.ordering.core.ports.out.orders.OrderDetailOutput;
import com.algashop.ordering.core.ports.out.orders.OrderSummaryOutput;
import com.algashop.ordering.core.ports.in.order.ForQueryingOrders;
import com.algashop.ordering.core.ports.in.order.OrderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryService implements ForQueryingOrders {

    private final ForObtainingOrders forObtainingOrders;

    public OrderDetailOutput findById(String id) {
        return forObtainingOrders.findById(id);
    }

    public Page<OrderSummaryOutput> filter(OrderFilter filter) {
        return forObtainingOrders.filter(filter);
    }
}
