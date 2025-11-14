package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.DomainService;
import com.algashop.ordering.core.domain.commons.Money;
import com.algashop.ordering.core.domain.customer.Customer;
import com.algashop.ordering.core.domain.product.Product;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCart;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCartItem;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCartCantProceedToCheckoutException;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@DomainService
@RequiredArgsConstructor
public class CheckoutService {

    private final CustomerHaveFreeShippingSpecification haveFreeShippingSpecification;

    public Order checkout(Customer customer,
            ShoppingCart shoppingCart,
            Billing billing,
            Shipping shipping,
            PaymentMethod paymentMethod,
            CreditCardId creditCardId) {

        if (shoppingCart.isEmpty()) {
            throw new ShoppingCartCantProceedToCheckoutException();
        }

        if (shoppingCart.containsUnavailableItems()) {
            throw new ShoppingCartCantProceedToCheckoutException();
        }

        Set<ShoppingCartItem> items = shoppingCart.items();

        Order order = Order.draft(shoppingCart.customerId());
        order.changeBilling(billing);

        if (haveFreeShipping(customer)) {
            Shipping freeShipping = shipping.toBuilder().cost(Money.ZERO).build();
            order.changeShipping(freeShipping);
        } else {
            order.changeShipping(shipping);
        }

        order.changePaymentMethod(paymentMethod, creditCardId);

        for (ShoppingCartItem item : items) {
            order.addItem(new Product(item.productId(), item.name(),
                    item.price(), item.isAvailable()), item.quantity());
        }

        order.place();
        shoppingCart.empty();

        return order;
    }

    private boolean haveFreeShipping(Customer customer) {
        return haveFreeShippingSpecification.isSatisfiedBy(customer);
    }

}
