package com.algashop.ordering.core.domain.customer;

import com.algashop.ordering.core.domain.Repository;
import com.algashop.ordering.core.domain.commons.Email;

import java.util.Optional;

public interface Customers extends Repository<Customer, CustomerId> {
    Optional<Customer> ofEmail(Email email);
    boolean isEmailUnique(Email email, CustomerId exceptCustomerId);
}
