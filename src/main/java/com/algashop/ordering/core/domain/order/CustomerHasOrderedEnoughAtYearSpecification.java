package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.Specification;
import com.algashop.ordering.core.domain.customer.Customer;
import lombok.RequiredArgsConstructor;

import java.time.Year;

@RequiredArgsConstructor
public class CustomerHasOrderedEnoughAtYearSpecification
        implements Specification<Customer> {

    private final Orders orders;
    private final long expectedOrderCount;

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return orders.salesQuantityByCustomerInYear(
                customer.id(),
                Year.now()
        ) >= expectedOrderCount;
    }
}
