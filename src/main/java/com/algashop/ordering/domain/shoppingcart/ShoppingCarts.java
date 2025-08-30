package com.algashop.ordering.domain.shoppingcart;

import com.algashop.ordering.domain.RemoveCapableRepository;
import com.algashop.ordering.domain.customer.CustomerId;

import java.util.Optional;

public interface ShoppingCarts extends RemoveCapableRepository<ShoppingCart, ShoppingCartId> {
    Optional<ShoppingCart> ofCustomer(CustomerId customerId);
}
