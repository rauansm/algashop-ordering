package com.algashop.ordering.infrastructure.adapters.out.persistence.shoppingcart;

import com.algashop.ordering.core.domain.shoppingcart.ShoppingCart;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCartItem;
import com.algashop.ordering.core.domain.commons.Money;
import com.algashop.ordering.core.domain.product.ProductName;
import com.algashop.ordering.core.domain.commons.Quantity;
import com.algashop.ordering.core.domain.customer.CustomerId;
import com.algashop.ordering.core.domain.product.ProductId;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCartId;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCartItemId;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ShoppingCartPersistenceEntityDisassembler {
    public ShoppingCart toDomainEntity(ShoppingCartPersistenceEntity source) {
        return ShoppingCart.existing()
                .id(new ShoppingCartId(source.getId()))
                .customerId(new CustomerId(source.getCustomerId()))
                .totalAmount(new Money(source.getTotalAmount()))
                .totalItems(new Quantity(source.getTotalItems()))
                .createdAt(source.getCreatedAt())
                .items(toItemsDomainEntities(source.getItems()))
                .build();
    }

    private Set<ShoppingCartItem> toItemsDomainEntities(Set<ShoppingCartItemPersistenceEntity> source) {
        return source.stream().map(this::toItemEntity).collect(Collectors.toSet());
    }

    private ShoppingCartItem toItemEntity(ShoppingCartItemPersistenceEntity source) {
        return ShoppingCartItem.existing()
                .id(new ShoppingCartItemId(source.getId()))
                .shoppingCartId(new ShoppingCartId(source.getShoppingCartId()))
                .productId(new ProductId(source.getProductId()))
                .productName(new ProductName(source.getName()))
                .price(new Money(source.getPrice()))
                .quantity(new Quantity(source.getQuantity()))
                .available(source.getAvailable())
                .totalAmount(new Money(source.getTotalAmount()))
                .build();
    }
}
