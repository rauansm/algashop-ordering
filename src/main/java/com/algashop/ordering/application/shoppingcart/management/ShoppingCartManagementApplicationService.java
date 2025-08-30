package com.algashop.ordering.application.shoppingcart.management;

import com.algashop.ordering.domain.commons.Quantity;
import com.algashop.ordering.domain.customer.CustomerId;
import com.algashop.ordering.domain.product.Product;
import com.algashop.ordering.domain.product.ProductCatalogService;
import com.algashop.ordering.domain.product.ProductId;
import com.algashop.ordering.domain.product.ProductNotFoundException;
import com.algashop.ordering.domain.shoppingcart.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingCartManagementApplicationService {

    private final ShoppingCarts shoppingCarts;
    private final ProductCatalogService productCatalogService;
    private final ShoppingService shoppingService;

    @Transactional
    public void addItem(ShoppingCartItemInput input) {
        Objects.requireNonNull(input);
        ShoppingCartId shoppingCartId = new ShoppingCartId(input.getShoppingCartId());
        ProductId productId = new ProductId(input.getProductId());

        ShoppingCart shoppingCart = shoppingCarts.ofId(shoppingCartId)
                .orElseThrow(ShoppingCartNotFoundException::new);

        Product product = productCatalogService.ofId(productId)
                .orElseThrow(ProductNotFoundException::new);

        shoppingCart.addItem(product, new Quantity(input.getQuantity()));

        shoppingCarts.add(shoppingCart);
    }

    @Transactional
    public UUID createNew(UUID rawCustomerId) {
        Objects.requireNonNull(rawCustomerId);
        ShoppingCart shoppingCart = shoppingService.startShopping(new CustomerId(rawCustomerId));
        shoppingCarts.add(shoppingCart);
        return shoppingCart.id().value();
    }

    @Transactional
    public void removeItem(UUID rawShoppingCartId, UUID rawShoppingCartItemId) {
        Objects.requireNonNull(rawShoppingCartId);
        Objects.requireNonNull(rawShoppingCartItemId);
        ShoppingCartId shoppingCartId = new ShoppingCartId(rawShoppingCartId);
        ShoppingCart shoppingCart = shoppingCarts.ofId(shoppingCartId)
                .orElseThrow(ShoppingCartNotFoundException::new);
        shoppingCart.removeItem(new ShoppingCartItemId(rawShoppingCartItemId));
        shoppingCarts.add(shoppingCart);
    }

    @Transactional
    public void empty(UUID rawShoppingCartId) {
        Objects.requireNonNull(rawShoppingCartId);
        ShoppingCartId shoppingCartId = new ShoppingCartId(rawShoppingCartId);
        ShoppingCart shoppingCart = shoppingCarts.ofId(shoppingCartId)
                .orElseThrow(ShoppingCartNotFoundException::new);
        shoppingCart.empty();
        shoppingCarts.add(shoppingCart);
    }

    @Transactional
    public void delete(UUID rawShoppingCartId) {
        Objects.requireNonNull(rawShoppingCartId);
        ShoppingCartId shoppingCartId = new ShoppingCartId(rawShoppingCartId);
        ShoppingCart shoppingCart = shoppingCarts.ofId(shoppingCartId)
                .orElseThrow(ShoppingCartNotFoundException::new);
        shoppingCarts.remove(shoppingCart);
    }
}
