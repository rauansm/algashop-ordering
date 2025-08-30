package com.algashop.ordering.infrastructure.beans;

import com.algashop.ordering.domain.customer.LoyaltyPoints;
import com.algashop.ordering.domain.order.CustomerHaveFreeShippingSpecification;
import com.algashop.ordering.domain.order.Orders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpecificationBeansConfig {

    @Bean
    public CustomerHaveFreeShippingSpecification customerHaveFreeShippingSpecification(Orders orders) {
        return new CustomerHaveFreeShippingSpecification(
                orders,
                new LoyaltyPoints(200),
                2L,
                new LoyaltyPoints(2000)
        );
    }

}
