package com.algashop.ordering.domain.service;

import com.algashop.ordering.domain.valueobject.Product;
import com.algashop.ordering.domain.valueobject.id.ProductId;

import java.util.Optional;

public interface ProductCatalogService {
    Optional<Product> ofId(ProductId productId);
}
