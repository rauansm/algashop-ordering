package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.commons.Address;
import com.algashop.ordering.core.domain.commons.Money;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

@Builder(toBuilder = true)
public record Shipping(Money cost, LocalDate expectedDate, Recipient recipient, Address address) {

    public Shipping {
        Objects.requireNonNull(recipient);
        Objects.requireNonNull(cost);
        Objects.requireNonNull(expectedDate);
        Objects.requireNonNull(address);
    }
}
