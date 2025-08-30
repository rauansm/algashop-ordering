package com.algashop.ordering.domain;

public interface AggregateRoot<ID> extends DomainEventSource {
    ID id();
}
