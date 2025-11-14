package com.algashop.ordering.core.domain.shoppingcart;

import com.algashop.ordering.core.domain.customer.CustomerId;
import com.algashop.ordering.core.domain.RemoveCapableRepository;

import java.util.Optional;

public interface ShoppingCarts extends RemoveCapableRepository<ShoppingCart, ShoppingCartId> {
    Optional<ShoppingCart> ofCustomer(CustomerId customerId);
}
