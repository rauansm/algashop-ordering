package com.algashop.ordering.domain.order;

import com.algashop.ordering.domain.DomainException;
import com.algashop.ordering.domain.ErrorMessages;

public class OrderInvalidShippingDeliveryDateException extends DomainException {

    public OrderInvalidShippingDeliveryDateException(OrderId id) {
        super(String.format(ErrorMessages.ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST, id));
    }
}
