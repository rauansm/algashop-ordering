package com.algashop.ordering.infrastructure.adapters.out.persistence.shoppingcart;

import com.algashop.ordering.core.ports.in.shoppingcart.ShoppingCartOutput;
import com.algashop.ordering.core.application.utility.Mapper;
import com.algashop.ordering.core.domain.shoppingcart.ShoppingCartNotFoundException;
import com.algashop.ordering.core.ports.out.shoppingcart.ForObtainingShoppingCarts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class ForObtainingShoppingCartsJpaRepositoryImpl implements ForObtainingShoppingCarts {

    private final ShoppingCartPersistenceEntityRepository persistenceRepository;
    private final Mapper mapper;

    @Override
    public ShoppingCartOutput findById(UUID shoppingCartId) {
        return persistenceRepository.findById(shoppingCartId)
                .map(s -> mapper.convert(s, ShoppingCartOutput.class))
                .orElseThrow(ShoppingCartNotFoundException::new);
    }

    @Override
    public ShoppingCartOutput findByCustomerId(UUID customerId) {
        return persistenceRepository.findByCustomer_Id(customerId)
                .map(s -> mapper.convert(s, ShoppingCartOutput.class))
                .orElseThrow(ShoppingCartNotFoundException::new);
    }
}
