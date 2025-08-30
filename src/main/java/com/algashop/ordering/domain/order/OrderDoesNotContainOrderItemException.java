package com.algashop.ordering.domain.order;

import com.algashop.ordering.domain.DomainException;
import com.algashop.ordering.domain.ErrorMessages;

public class OrderDoesNotContainOrderItemException extends DomainException {

    public OrderDoesNotContainOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAIN_ITEM, id, orderItemId));
    }
}
