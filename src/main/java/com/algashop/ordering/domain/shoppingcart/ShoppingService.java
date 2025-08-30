package com.algashop.ordering.domain.shoppingcart;

import com.algashop.ordering.domain.customer.CustomerAlreadyHaveShoppingCartException;
import com.algashop.ordering.domain.customer.CustomerNotFoundException;
import com.algashop.ordering.domain.customer.Customers;
import com.algashop.ordering.domain.DomainService;
import com.algashop.ordering.domain.customer.CustomerId;
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
