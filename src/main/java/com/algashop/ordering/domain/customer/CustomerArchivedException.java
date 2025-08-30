package com.algashop.ordering.domain.customer;

import com.algashop.ordering.domain.DomainException;

import static com.algashop.ordering.domain.ErrorMessages.ERROR_CUSTOMER_ARCHIVED;

public class CustomerArchivedException extends DomainException {

    public CustomerArchivedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerArchivedException() {
        super(ERROR_CUSTOMER_ARCHIVED);
    }
}
