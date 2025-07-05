package com.algashop.ordering.domain.valueobject;

import lombok.Builder;
import org.springframework.cglib.core.Local;

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
