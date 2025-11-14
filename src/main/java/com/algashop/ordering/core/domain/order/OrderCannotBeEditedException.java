package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.DomainException;
import com.algashop.ordering.core.domain.ErrorMessages;

public class OrderCannotBeEditedException extends DomainException {

    public OrderCannotBeEditedException(OrderId id, OrderStatus status) {
        super(String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED, id, status));
    }

}
