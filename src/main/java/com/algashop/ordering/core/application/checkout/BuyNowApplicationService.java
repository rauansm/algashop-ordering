package com.algashop.ordering.core.application.checkout;

import com.algashop.ordering.core.application.order.BillingInputDisassembler;
import com.algashop.ordering.core.application.order.ShippingInputDisassembler;
import com.algashop.ordering.core.domain.DomainException;
import com.algashop.ordering.core.domain.commons.Quantity;
import com.algashop.ordering.core.domain.commons.ZipCode;
import com.algashop.ordering.core.domain.customer.Customer;
import com.algashop.ordering.core.domain.customer.CustomerId;
import com.algashop.ordering.core.domain.customer.CustomerNotFoundException;
import com.algashop.ordering.core.domain.customer.Customers;
import com.algashop.ordering.core.domain.order.*;
import com.algashop.ordering.core.domain.order.shipping.OriginAddressService;
import com.algashop.ordering.core.domain.order.shipping.ShippingCostService;
import com.algashop.ordering.core.domain.product.Product;
import com.algashop.ordering.core.domain.product.ProductCatalogService;
import com.algashop.ordering.core.domain.product.ProductId;
import com.algashop.ordering.core.domain.product.ProductNotFoundException;
import com.algashop.ordering.core.ports.in.checkout.BuyNowInput;
import com.algashop.ordering.core.ports.in.checkout.ForBuyingProduct;
import com.algashop.ordering.core.ports.in.order.ShippingInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BuyNowApplicationService implements ForBuyingProduct {

    private final BuyNowService buyNowService;
    private final ProductCatalogService productCatalogService;

    private final ShippingCostService shippingCostService;
    private final OriginAddressService originAddressService;

    private final Orders orders;
    private final Customers customers;

    private final ShippingInputDisassembler shippingInputDisassembler;
    private final BillingInputDisassembler billingInputDisassembler;

    @Transactional
    public String buyNow(BuyNowInput input) {
        Objects.requireNonNull(input);

        PaymentMethod paymentMethod = PaymentMethod.valueOf(input.getPaymentMethod());
        CustomerId customerId = new CustomerId(input.getCustomerId());
        Quantity quantity = new Quantity(input.getQuantity());
        CreditCardId creditCardId = null;

        if (paymentMethod.equals(PaymentMethod.CREDIT_CARD)) {
            if (input.getCreditCardId() == null) {
                throw new DomainException("Credit card id is required");
            }
            creditCardId = new CreditCardId(input.getCreditCardId());
        }

        Customer customer = customers.ofId(customerId).orElseThrow(CustomerNotFoundException::new);

        Product product = findProduct(new ProductId(input.getProductId()));

        var shippingCalculationResult = calculateShippingCost(input.getShipping());

        Shipping shipping = shippingInputDisassembler.toDomainModel(input.getShipping(),
                shippingCalculationResult);

        Billing billing = billingInputDisassembler.toDomainModel(input.getBilling());

        Order order = buyNowService.buyNow(product, customer, billing, shipping, quantity, paymentMethod, creditCardId);

        orders.add(order);

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