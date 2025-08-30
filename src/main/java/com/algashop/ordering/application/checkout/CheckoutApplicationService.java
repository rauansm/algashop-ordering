package com.algashop.ordering.application.checkout;

import com.algashop.ordering.domain.commons.ZipCode;
import com.algashop.ordering.domain.customer.Customer;
import com.algashop.ordering.domain.customer.CustomerNotFoundException;
import com.algashop.ordering.domain.customer.Customers;
import com.algashop.ordering.domain.order.CheckoutService;
import com.algashop.ordering.domain.order.Order;
import com.algashop.ordering.domain.order.Orders;
import com.algashop.ordering.domain.order.PaymentMethod;
import com.algashop.ordering.domain.order.shipping.OriginAddressService;
import com.algashop.ordering.domain.order.shipping.ShippingCostService;
import com.algashop.ordering.domain.product.Product;
import com.algashop.ordering.domain.product.ProductCatalogService;
import com.algashop.ordering.domain.product.ProductId;
import com.algashop.ordering.domain.product.ProductNotFoundException;
import com.algashop.ordering.domain.shoppingcart.ShoppingCart;
import com.algashop.ordering.domain.shoppingcart.ShoppingCartId;
import com.algashop.ordering.domain.shoppingcart.ShoppingCartNotFoundException;
import com.algashop.ordering.domain.shoppingcart.ShoppingCarts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckoutApplicationService {

    private final Orders orders;
    private final ShoppingCarts shoppingCarts;
    private final Customers customers;

    private final CheckoutService checkoutService;

    private final BillingInputDisassembler billingInputDisassembler;
    private final ShippingInputDisassembler shippingInputDisassembler;

    private final ShippingCostService shippingCostService;
    private final OriginAddressService originAddressService;
    private final ProductCatalogService productCatalogService;

    @Transactional
    public String checkout(CheckoutInput input) {
        Objects.requireNonNull(input);
        PaymentMethod paymentMethod = PaymentMethod.valueOf(input.getPaymentMethod());

        ShoppingCartId shoppingCartId = new ShoppingCartId(input.getShoppingCartId());
        ShoppingCart shoppingCart = shoppingCarts.ofId(shoppingCartId)
                .orElseThrow(ShoppingCartNotFoundException::new);

        Customer customer = customers.ofId(shoppingCart.customerId()).orElseThrow(CustomerNotFoundException::new);

        var shippingCalculationResult = calculateShippingCost(input.getShipping());

        Order order = checkoutService.checkout(customer, shoppingCart,
                billingInputDisassembler.toDomainModel(input.getBilling()),
                shippingInputDisassembler.toDomainModel(input.getShipping(), shippingCalculationResult),
                paymentMethod);

        orders.add(order);
        shoppingCarts.add(shoppingCart);

        return order.id().toString();
    }

    private ShippingCostService.CalculationResult calculateShippingCost(ShippingInput shipping) {
        ZipCode origin = originAddressService.originAddress().zipCode();
        ZipCode destination = new ZipCode(shipping.getAddress().getZipCode());
        return shippingCostService.calculate(new ShippingCostService.CalculationRequest(origin, destination));
    }

    private Product findProduct(ProductId productId) {
        return productCatalogService.ofId(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

}
