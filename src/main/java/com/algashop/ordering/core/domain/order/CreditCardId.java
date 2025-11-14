package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record CreditCardId(UUID id) {
    public CreditCardId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    public CreditCardId {
        Objects.requireNonNull(id);
    }
}
