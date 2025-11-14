package com.algashop.ordering.infrastructure.adapters.out.web.shipping.client.fake;

import com.algashop.ordering.core.domain.order.shipping.OriginAddressService;
import com.algashop.ordering.core.domain.commons.Address;
import com.algashop.ordering.core.domain.commons.ZipCode;
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
