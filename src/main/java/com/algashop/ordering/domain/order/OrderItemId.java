package com.algashop.ordering.domain.order;

import com.algashop.ordering.domain.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record OrderItemId(UUID value) {

    public OrderItemId {
        Objects.requireNonNull(value);
    }

    public OrderItemId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    public OrderItemId(String value) {
        this(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
