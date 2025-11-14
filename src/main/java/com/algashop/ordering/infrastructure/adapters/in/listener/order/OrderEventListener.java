package com.algashop.ordering.infrastructure.adapters.in.listener.order;

import com.algashop.ordering.core.domain.order.OrderCanceledEvent;
import com.algashop.ordering.core.domain.order.OrderPaidEvent;
import com.algashop.ordering.core.domain.order.OrderPlacedEvent;
import com.algashop.ordering.core.domain.order.OrderReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @EventListener
    public void listen(OrderPlacedEvent event) {

    }

    @EventListener
    public void listen(OrderPaidEvent event) {

    }

    @EventListener
    public void listen(OrderReadyEvent event) {

    }

    @EventListener
    public void listen(OrderCanceledEvent event) {

    }

}
