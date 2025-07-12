package com.algashop.ordering.domain.repository;

import com.algashop.ordering.domain.entity.Customer;
import com.algashop.ordering.domain.valueobject.Email;
import com.algashop.ordering.domain.valueobject.id.CustomerId;

import java.util.Optional;

public interface Customers extends Repository<Customer, CustomerId> {
    Optional<Customer> ofEmail(Email email);
    boolean isEmailUnique(Email email, CustomerId exceptCustomerId);
}
