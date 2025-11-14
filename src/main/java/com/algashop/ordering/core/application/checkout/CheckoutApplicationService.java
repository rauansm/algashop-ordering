package com.algashop.ordering.core.application.checkout;

import com.algashop.ordering.core.application.order.BillingInputDisassembler;
import com.algashop.ordering.core.application.order.ShippingInputDisassembler;
import com.algashop.ordering.core.domain.DomainException;
import com.algashop.ordering.core.domain.commons.ZipCode;
import com.algashop.ordering.core.domain.customer.Customer;
import com.algashop.ordering.core.domain.customer.CustomerNotFoundException;
import com.algashop.ordering.core.domain.customer.Customers;
import com.algashop.ordering.core.domain.order.*;
import com.algashop.ordering.core.domain.order.shipping.OriginAddressService;
import com.algashop.ordering.core.domain.order.shipping.ShippingCostService;
import com.algashop.ordering.core.domain.product.Product;
import com.algashop.ordering.core.domain.product.ProductCatalogService;
import com.algashop.ordering.core.domain.product.ProductId;
import com.algashop.ordering.core.domain.product.ProductNotFoundException;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCart;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCartId;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCartNotFoundException;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCarts;
import com.algashop.ordering.core.ports.in.checkout.CheckoutInput;
import com.algashop.ordering.core.ports.in.checkout.ForBuyingWithShoppingCart;
import com.algashop.ordering.core.ports.in.order.ShippingInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckoutApplicationService implements ForBuyingWithShoppingCart {

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

        CreditCardId creditCardId = null;

        if (paymentMethod.equals(PaymentMethod.CREDIT_CARD)) {
            if (input.getCreditCardId() == null) {
                throw new DomainException("Credit card id is required");
            }
            creditCardId = new CreditCardId(input.getCreditCardId());
        }

        ShoppingCartId shoppingCartId = new ShoppingCartId(input.getShoppingCartId());
        ShoppingCart shoppingCart = shoppingCarts.ofId(shoppingCartId)
                .orElseThrow(ShoppingCartNotFoundException::new);

        Customer customer = customers.ofId(shoppingCart.customerId()).orElseThrow(CustomerNotFoundException::new);

        var shippingCalculationResult = calculateShippingCost(input.getShipping());

        Order order = checkoutService.checkout(customer, shoppingCart,
                billingInputDisassembler.toDomainModel(input.getBilling()),
                shippingInputDisassembler.toDomainModel(input.getShipping(), shippingCalculationResult),
                paymentMethod, creditCardId);

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
