package com.algashop.ordering.domain.valueobject.id;

import com.algashop.ordering.domain.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {

    public CustomerId {
        Objects.requireNonNull(value);
    }

    public CustomerId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
