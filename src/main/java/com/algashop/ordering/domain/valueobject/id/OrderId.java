package com.algashop.ordering.domain.valueobject.id;

import com.algashop.ordering.domain.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record OrderId(UUID value) {

    public OrderId {
        Objects.requireNonNull(value);
    }

    public OrderId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
