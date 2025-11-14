package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.DomainException;
import com.algashop.ordering.core.domain.ErrorMessages;

public class OrderDoesNotContainOrderItemException extends DomainException {

    public OrderDoesNotContainOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAIN_ITEM, id, orderItemId));
    }
}
