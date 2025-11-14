package com.algashop.ordering.core.application.checkout;

import com.algashop.ordering.core.domain.commons.Money;
import com.algashop.ordering.core.domain.customer.CustomerTestDataBuilder;
import com.algashop.ordering.core.domain.customer.Customers;
import com.algashop.ordering.core.domain.order.OrderId;
import com.algashop.ordering.core.domain.order.Orders;
import com.algashop.ordering.core.domain.order.shipping.ShippingCostService;
import com.algashop.ordering.core.domain.product.Product;
import com.algashop.ordering.core.domain.product.ProductCatalogService;
import com.algashop.ordering.core.domain.product.ProductTestDataBuilder;
import com.algashop.ordering.core.ports.in.checkout.BuyNowInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Transactional
class BuyNowApplicationServiceIT {

    @Autowired
    private BuyNowApplicationService buyNowApplicationService;

    @Autowired
    private Orders orders;

    @Autowired
    private Customers customers;

    @MockitoBean
    private ProductCatalogService productCatalogService;

    @MockitoBean
    private ShippingCostService shippingCostService;

    @BeforeEach
    public void setup() {
        if (!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customers.add(CustomerTestDataBuilder.existingCustomer().build());
        }
    }

    @Test
    public void shouldBuyNow() {
        Product product = ProductTestDataBuilder.aProduct().build();
        Mockito.when(productCatalogService.ofId(product.id())).thenReturn(Optional.of(product));

        Mockito.when(shippingCostService.calculate(Mockito.any(ShippingCostService.CalculationRequest.class)))
                .thenReturn(new ShippingCostService.CalculationResult(
                        new Money("10.00"),
                        LocalDate.now().plusDays(3)
                ));

        BuyNowInput input = BuyNowInputTestDataBuilder.aBuyNowInput().build();

        String orderId = buyNowApplicationService.buyNow(input);

        Assertions.assertThat(orderId).isNotBlank();
        Assertions.assertThat(orders.exists(new OrderId(orderId))).isTrue();
    }

}