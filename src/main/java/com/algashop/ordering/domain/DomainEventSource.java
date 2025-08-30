package com.algashop.ordering.domain;

import java.util.List;

public interface DomainEventSource {
    List<Object> domainEvents();
    void clearDomainEvents();
}
