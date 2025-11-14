package com.algashop.ordering.core.domain.shoppingcart;

import com.algashop.ordering.core.domain.DomainService;
import com.algashop.ordering.core.domain.customer.CustomerId;
import com.algashop.ordering.core.domain.customer.Customers;
import com.algashop.ordering.core.domain.customer.CustomerAlreadyHaveShoppingCartException;
import com.algashop.ordering.core.domain.customer.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ShoppingService {

    private final ShoppingCarts shoppingCarts;
    private final Customers customers;

    public ShoppingCart startShopping(CustomerId customerId) {
        if (!customers.exists(customerId)) {
            throw new CustomerNotFoundException();
        }

        if (shoppingCarts.ofCustomer(customerId).isPresent()) {
            throw new CustomerAlreadyHaveShoppingCartException();
        }

        return ShoppingCart.startShopping(customerId);
    }

}
