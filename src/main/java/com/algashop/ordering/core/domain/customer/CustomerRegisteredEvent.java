package com.algashop.ordering.core.domain.customer;

import com.algashop.ordering.core.domain.commons.Email;
import com.algashop.ordering.core.domain.commons.FullName;

import java.time.OffsetDateTime;

public record CustomerRegisteredEvent(CustomerId customerId,
                                      OffsetDateTime registeredAt,
                                      FullName fullName,
                                      Email email) {
}
