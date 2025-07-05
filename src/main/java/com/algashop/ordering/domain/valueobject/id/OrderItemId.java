package com.algashop.ordering.domain.valueobject.id;

import com.algashop.ordering.domain.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record OrderItemId(UUID value) {

    public OrderItemId {
        Objects.requireNonNull(value);
    }

    public OrderItemId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
