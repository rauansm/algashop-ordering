package com.algashop.ordering.core.domain.order.shipping;

import com.algashop.ordering.core.domain.order.shipping.OriginAddressService;
import com.algashop.ordering.core.domain.order.shipping.ShippingCostService;
import com.algashop.ordering.core.domain.order.shipping.ShippingCostService.CalculationRequest;
import com.algashop.ordering.core.domain.commons.ZipCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShippingCostServiceIT {

    @Autowired
    private ShippingCostService shippingCostService;

    @Autowired
    private OriginAddressService originAddressService;

    @Test
    void shouldCalculate() {
        ZipCode origin = originAddressService.originAddress().zipCode();
        ZipCode destination = new ZipCode("12345");

        var calculate = shippingCostService
                .calculate(new CalculationRequest(origin, destination));

        Assertions.assertThat(calculate.cost()).isNotNull();
        Assertions.assertThat(calculate.expectedDate()).isNotNull();
    }

}