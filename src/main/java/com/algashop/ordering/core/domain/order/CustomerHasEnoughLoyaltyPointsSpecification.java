package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.Specification;
import com.algashop.ordering.core.domain.customer.Customer;
import com.algashop.ordering.core.domain.customer.LoyaltyPoints;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerHasEnoughLoyaltyPointsSpecification
        implements Specification<Customer> {

    private final LoyaltyPoints expectedLoyaltyPoints;

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return customer.loyaltyPoints().compareTo(expectedLoyaltyPoints) >= 0;
    }
}