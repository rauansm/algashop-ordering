package com.algashop.ordering.domain.customer;

import com.algashop.ordering.domain.commons.Email;
import com.algashop.ordering.domain.commons.FullName;

import java.time.OffsetDateTime;

public record CustomerRegisteredEvent(CustomerId customerId,
                                      OffsetDateTime registeredAt,
                                      FullName fullName,
                                      Email email) {
}
