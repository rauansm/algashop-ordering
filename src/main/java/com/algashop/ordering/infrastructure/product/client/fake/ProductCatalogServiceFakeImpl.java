package com.algashop.ordering.infrastructure.product.client.fake;

import com.algashop.ordering.domain.product.ProductCatalogService;
import com.algashop.ordering.domain.commons.Money;
import com.algashop.ordering.domain.product.Product;
import com.algashop.ordering.domain.product.ProductName;
import com.algashop.ordering.domain.product.ProductId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductCatalogServiceFakeImpl implements ProductCatalogService {

    @Override
    public Optional<Product> ofId(ProductId productId) {
        Product product = Product.builder().id(productId)
                .inStock(true)
                .name(new ProductName("Notebook"))
                .price(new Money("3000"))
                .build();
        return Optional.of(product);
    }
}
