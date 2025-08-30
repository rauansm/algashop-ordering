package com.algashop.ordering.domain.customer;

import java.time.OffsetDateTime;

public record CustomerArchivedEvent(CustomerId customerId, OffsetDateTime archivedAt) {
}
