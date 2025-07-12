package com.algashop.ordering.infrastructure.persistence.disassembler;

import com.algashop.ordering.domain.entity.ShoppingCart;
import com.algashop.ordering.domain.entity.ShoppingCartItem;
import com.algashop.ordering.domain.valueobject.Money;
import com.algashop.ordering.domain.valueobject.ProductName;
import com.algashop.ordering.domain.valueobject.Quantity;
import com.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algashop.ordering.domain.valueobject.id.ProductId;
import com.algashop.ordering.domain.valueobject.id.ShoppingCartId;
import com.algashop.ordering.domain.valueobject.id.ShoppingCartItemId;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
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
