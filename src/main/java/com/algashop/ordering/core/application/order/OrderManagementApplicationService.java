package com.algashop.ordering.core.application.order;

import com.algashop.ordering.core.domain.order.Order;
import com.algashop.ordering.core.domain.order.OrderId;
import com.algashop.ordering.core.domain.order.OrderNotFoundException;
import com.algashop.ordering.core.domain.order.Orders;
import com.algashop.ordering.core.ports.in.order.ForManagingOrders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderManagementApplicationService implements ForManagingOrders {

    private final Orders orders;

    @Override
    @Transactional
    public void cancel(String rawOrderId) {
        Order order = findOrder(rawOrderId);
        order.cancel();
        orders.add(order);
    }

    @Override
    @Transactional
    public void markAsPaid(String rawOrderId) {
        Order order = findOrder(rawOrderId);
        order.markAsPaid();
        orders.add(order);
    }

    @Override
    @Transactional
    public void markAsReady(String rawOrderId) {
        Order order = findOrder(rawOrderId);
        order.markAsReady();
        orders.add(order);
    }

    private Order findOrder(String rawOrderId) {
        OrderId orderId = new OrderId(rawOrderId);
        return orders.ofId(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

}
