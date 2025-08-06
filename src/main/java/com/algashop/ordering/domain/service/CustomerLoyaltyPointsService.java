package com.algashop.ordering.domain.service;

import com.algashop.ordering.domain.entity.Customer;
import com.algashop.ordering.domain.entity.Order;
import com.algashop.ordering.domain.exception.CantAddLoyaltyPointsOrderIsNotReady;
import com.algashop.ordering.domain.exception.OrderNotBelongsToCustomerException;
import com.algashop.ordering.domain.valueobject.LoyaltyPoints;
import com.algashop.ordering.domain.valueobject.Money;

import java.util.Objects;

public class CustomerLoyaltyPointsService {

    private static final LoyaltyPoints basePoints = new LoyaltyPoints(5);

    private static final Money expectedAmountToGivePoints = new Money("1000");

    public void addPoints(Customer customer, Order order) {
        Objects.requireNonNull(customer);
        Objects.requireNonNull(order);

        if (!customer.id().equals(order.customerId())) {
            throw new OrderNotBelongsToCustomerException();
        }

        if (!order.isReady()) {
            throw new CantAddLoyaltyPointsOrderIsNotReady();
        }

        customer.addLoyaltyPoints(calculatePoints(order));
    }

    private LoyaltyPoints calculatePoints(Order order) {
        if (shouldGivePointsByAmount(order.totalAmount())) {
            Money result = order.totalAmount().divide(expectedAmountToGivePoints);
            return new LoyaltyPoints(result.value().intValue() * basePoints.value());
        }

        return LoyaltyPoints.ZERO;
    }

    private boolean shouldGivePointsByAmount(Money amount) {
        return amount.compareTo(expectedAmountToGivePoints) >= 0;
    }
}
