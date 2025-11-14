package com.algashop.ordering.core.application.order;

import com.algashop.ordering.core.ports.in.order.ShippingInput;
import com.algashop.ordering.core.ports.in.commons.AddressData;
import com.algashop.ordering.core.domain.commons.*;
import com.algashop.ordering.core.domain.order.Recipient;
import com.algashop.ordering.core.domain.order.Shipping;
import com.algashop.ordering.core.domain.order.shipping.ShippingCostService;
import org.springframework.stereotype.Component;

@Component
public class ShippingInputDisassembler {

    public Shipping toDomainModel(ShippingInput shippingInput,
            ShippingCostService.CalculationResult shippingCalculationResult) {
        AddressData address = shippingInput.getAddress();
        return Shipping.builder()
                .cost(shippingCalculationResult.cost())
                .expectedDate(shippingCalculationResult.expectedDate())
                .recipient(Recipient.builder()
                        .fullName(new FullName(
                                shippingInput.getRecipient().getFirstName(),
                                shippingInput.getRecipient().getLastName()))
                        .document(new Document(shippingInput.getRecipient().getDocument()))
                        .phone(new Phone(shippingInput.getRecipient().getPhone()))
                        .build())
                .address(Address.builder()
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .neighborhood(address.getNeighborhood())
                        .city(address.getCity())
                        .state(address.getState())
                        .zipCode(new ZipCode(address.getZipCode()))
                        .build())
                .build();
    }
}
