package com.algashop.ordering.core.domain.customer;

import com.algashop.ordering.core.domain.DomainException;
import com.algashop.ordering.core.domain.ErrorMessages;

public class CustomerArchivedException extends DomainException {

    public CustomerArchivedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerArchivedException() {
        super(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);
    }
}
