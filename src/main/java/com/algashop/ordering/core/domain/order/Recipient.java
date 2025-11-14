package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.commons.Document;
import com.algashop.ordering.core.domain.commons.FullName;
import com.algashop.ordering.core.domain.commons.Phone;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Recipient(FullName fullName, Document document, Phone phone) {

    public Recipient {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
    }
}
