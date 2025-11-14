package com.algashop.ordering.core.domain.order.shipping;

import com.algashop.ordering.core.domain.commons.Money;
import com.algashop.ordering.core.domain.commons.ZipCode;
import lombok.Builder;

import java.time.LocalDate;

public interface ShippingCostService {
    CalculationResult calculate(CalculationRequest request);

    @Builder
    record CalculationRequest(ZipCode origin, ZipCode destination) {

    }
    @Builder
    record CalculationResult(Money cost, LocalDate expectedDate) {

    }
}
