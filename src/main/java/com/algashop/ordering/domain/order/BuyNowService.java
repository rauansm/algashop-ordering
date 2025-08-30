package com.algashop.ordering.domain.order;

import com.algashop.ordering.domain.DomainService;
import com.algashop.ordering.domain.commons.Money;
import com.algashop.ordering.domain.customer.Customer;
import com.algashop.ordering.domain.customer.LoyaltyPoints;
import com.algashop.ordering.domain.product.Product;
import com.algashop.ordering.domain.commons.Quantity;
import com.algashop.ordering.domain.customer.CustomerId;
import lombok.RequiredArgsConstructor;

import java.time.Year;

@DomainService
@RequiredArgsConstructor
public class BuyNowService {

    private final CustomerHaveFreeShippingSpecification customerHaveFreeShippingSpecification;

    public Order buyNow(Product product,
            Customer customer,
            Billing billing,
            Shipping shipping,
            Quantity quantity,
            PaymentMethod paymentMethod) {

        product.checkOutOfStock();

        Order order = Order.draft(customer.id());
        order.changeBilling(billing);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, quantity);

        if (haveFreeShipping(customer)) {
            Shipping freeShipping = shipping.toBuilder().cost(Money.ZERO).build();
            order.changeShipping(freeShipping);
        } else {
            order.changeShipping(shipping);
        }

        order.place();

        return order;
    }

    private boolean haveFreeShipping(Customer customer) {
        return customerHaveFreeShippingSpecification.isSatisfiedBy(customer);
    }

}
