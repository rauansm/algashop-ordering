package com.algashop.ordering.core.domain;

public interface AggregateRoot<ID> extends DomainEventSource {
    ID id();
}
