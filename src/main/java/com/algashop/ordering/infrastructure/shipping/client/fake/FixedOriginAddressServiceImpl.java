package com.algashop.ordering.infrastructure.shipping.client.fake;

import com.algashop.ordering.domain.order.shipping.OriginAddressService;
import com.algashop.ordering.domain.commons.Address;
import com.algashop.ordering.domain.commons.ZipCode;
import org.springframework.stereotype.Component;

@Component
public class FixedOriginAddressServiceImpl implements OriginAddressService  {

    @Override
    public Address originAddress() {
        return Address.builder()
                .street("Bourbon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .build();
    }
}
