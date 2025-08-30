package com.algashop.ordering.application.order.management;

import com.algashop.ordering.domain.order.Order;
import com.algashop.ordering.domain.order.OrderId;
import com.algashop.ordering.domain.order.OrderNotFoundException;
import com.algashop.ordering.domain.order.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderManagementApplicationService {

    private final Orders orders;

    @Transactional
    public void cancel(String rawOrderId) {
        Order order = findOrder(rawOrderId);
        order.cancel();
        orders.add(order);
    }

    @Transactional
    public void markAsPaid(String rawOrderId) {
        Order order = findOrder(rawOrderId);
        order.markAsPaid();
        orders.add(order);
    }

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
