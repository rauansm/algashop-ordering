package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.DomainService;
import com.algashop.ordering.core.domain.commons.Money;
import com.algashop.ordering.core.domain.commons.Quantity;
import com.algashop.ordering.core.domain.customer.Customer;
import com.algashop.ordering.core.domain.product.Product;
import com.algashop.ordering.core.ports.in.checkout.ForBuyingProduct;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class BuyNowService {

    private final CustomerHaveFreeShippingSpecification customerHaveFreeShippingSpecification;

    public Order buyNow(Product product,
            Customer customer,
            Billing billing,
            Shipping shipping,
            Quantity quantity,
            PaymentMethod paymentMethod,
            CreditCardId creditCardId) {

        product.checkOutOfStock();

        Order order = Order.draft(customer.id());
        order.changeBilling(billing);
        order.changePaymentMethod(paymentMethod, creditCardId);
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
