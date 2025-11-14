package com.algashop.ordering.core.domain.order;

import com.algashop.ordering.core.domain.commons.Money;
import com.algashop.ordering.core.domain.commons.Quantity;
import com.algashop.ordering.core.domain.product.Product;
import com.algashop.ordering.core.domain.AbstractEventSourceEntity;
import com.algashop.ordering.core.domain.AggregateRoot;
import com.algashop.ordering.core.domain.customer.CustomerId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Order extends AbstractEventSourceEntity implements AggregateRoot<OrderId> {

    private OrderId id;
    private CustomerId customerId;

    private Money totalAmount;
    private Quantity totalItens;

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;

    private Billing billing;
    private Shipping shipping;

    private OrderStatus status;
    private PaymentMethod paymentMethod;

    private Set<OrderItem> itens = new HashSet<>();

    private Long version;

    private CreditCardId creditCardId;

    @Builder(builderClassName = "ExistingOrderBuilder", builderMethodName = "existing")
    public Order(OrderId id, Long version, CustomerId customerId,
            Money totalAmount, Quantity totalItems,
            OffsetDateTime placedAt, OffsetDateTime paidAt,
            OffsetDateTime canceledAt, OffsetDateTime readyAt,
            Billing billing, Shipping shipping,
            OrderStatus status, PaymentMethod paymentMethod,
            Set<OrderItem> items, CreditCardId creditCardId) {
        this.setId(id);
        this.setVersion(version);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setPlacedAt(placedAt);
        this.setPaidAt(paidAt);
        this.setCanceledAt(canceledAt);
        this.setReadyAt(readyAt);
        this.setBilling(billing);
        this.setShipping(shipping);
        this.setStatus(status);
        this.setPaymentMethod(paymentMethod);
        this.setItems(items);
        this.setCreditCardId(creditCardId);
    }

    public static Order draft(CustomerId customerId) {
        return new Order(
                new OrderId(),
                null,
                customerId,
                Money.ZERO,
                Quantity.ZERO,
                null,
                null,
                null,
                null,
                null,
                null,
                OrderStatus.DRAFT,
                null,
                new HashSet<>(),
                null
        );
    }

    public void addItem(Product product, Quantity quantity) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        verifyIfChangeable();

        product.checkOutOfStock();

        OrderItem orderItem = OrderItem.brandNew()
                .orderId(this.id())
                .quantity(quantity)
                .product(product)
                .build();

        this.itens.add(orderItem);

        this.recalculateTotals();
    }

    public void place() {
        this.verifyIfCanChangeToPlaced();
        this.setPlacedAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.PLACED);
        publishDomainEvent(new OrderPlacedEvent(this.id(), this.customerId(), this.placedAt()));
    }

    public void markAsPaid() {
        this.setPaidAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.PAID);
        publishDomainEvent(new OrderPaidEvent(this.id(), this.customerId(), this.paidAt()));
    }

    public void markAsReady() {
        this.changeStatus(OrderStatus.READY);
        this.setReadyAt(OffsetDateTime.now());
        publishDomainEvent(new OrderReadyEvent(this.id(), this.customerId(), this.readyAt()));
    }

    public void changePaymentMethod(PaymentMethod paymentMethod, CreditCardId creditCardId) {
        Objects.requireNonNull(paymentMethod);
        if (paymentMethod.equals(PaymentMethod.CREDIT_CARD)) {
            Objects.requireNonNull(creditCardId);
            this.setCreditCardId(creditCardId);
        }
        verifyIfChangeable();
        this.setPaymentMethod(paymentMethod);
    }

    public void changeBilling(Billing billing) {
        Objects.requireNonNull(billing);
        verifyIfChangeable();
        this.setBilling(billing);
    }

    public void changeShipping(Shipping newshipping) {
        Objects.requireNonNull(newshipping);

        verifyIfChangeable();

        if (newshipping.expectedDate().isBefore(LocalDate.now())) {
            throw new OrderInvalidShippingDeliveryDateException(this.id());
        }

        this.setShipping(newshipping);
    }

    public void changeItemQuantity(OrderItemId orderItemId, Quantity quantity) {
        Objects.requireNonNull(orderItemId);
        Objects.requireNonNull(quantity);

        verifyIfChangeable();

        OrderItem orderItem = findOrderItem(orderItemId);
        orderItem.changeQuantity(quantity);

        this.recalculateTotals();
    }

    public void removeItem(OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId);
        verifyIfChangeable();

        OrderItem orderItem = findOrderItem(orderItemId);
        this.itens.remove(orderItem);

        recalculateTotals();
    }

    public void cancel() {
        this.setCanceledAt(OffsetDateTime.now());
        this.changeStatus(OrderStatus.CANCELED);
        publishDomainEvent(new OrderCanceledEvent(this.id(), this.customerId(), this.canceledAt()));
    }

    public boolean isDraft() {
        return OrderStatus.DRAFT.equals(this.status());
    }

    public boolean isPlaced() {
        return OrderStatus.PLACED.equals(this.status());
    }

    public boolean isPaid() {
        return OrderStatus.PAID.equals(this.status());
    }

    public boolean isCanceled() {
        return OrderStatus.CANCELED.equals(this.status());
    }

    public boolean isReady() {
        return OrderStatus.READY.equals(this.status());
    }

    public OrderId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity totalItens() {
        return totalItens;
    }

    public OffsetDateTime placedAt() {
        return placedAt;
    }

    public OffsetDateTime paidAt() {
        return paidAt;
    }

    public OffsetDateTime canceledAt() {
        return canceledAt;
    }

    public OffsetDateTime readyAt() {
        return readyAt;
    }

    public Billing billing() {
        return billing;
    }

    public Shipping shipping() {
        return shipping;
    }

    public OrderStatus status() {
        return status;
    }

    public PaymentMethod paymentMethod() {
        return paymentMethod;
    }

    public Set<OrderItem> itens() {
        return Collections.unmodifiableSet(this.itens);
    }

    public CreditCardId creditCardId() {
        return creditCardId;
    }

    private void recalculateTotals() {
        BigDecimal totalItensAmount = this.itens.stream().map(i -> i.totalAmount().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalItensQuantity = this.itens.stream().map(i -> i.quantity().value())
                .reduce(0, Integer::sum);

        BigDecimal shippingCost;
        if (this.shipping == null) {
            shippingCost = BigDecimal.ZERO;
        } else {
            shippingCost = this.shipping.cost().value();
        }
        BigDecimal totalAmount = totalItensAmount.add(shippingCost);

        this.setTotalAmount(new Money(totalAmount));
        this.setTotalItems(new Quantity(totalItensQuantity));

    }

    private void changeStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus);
        if (this.status().canNotChangeTo(newStatus)) {
            throw new OrderStatusCannotBeChangedException(this.id, this.status, newStatus);
        }
        this.setStatus(newStatus);
    }

    private void verifyIfCanChangeToPlaced() {
        if (this.shipping() == null) {
            throw OrderCannotBePlacedException.noShippingInfo(this.id());
        }
        if (this.billing() == null) {
            throw OrderCannotBePlacedException.noBillingInfo(this.id());
        }
        if (this.paymentMethod() == null) {
            throw OrderCannotBePlacedException.noPaymentMethod(this.id());
        }
        if (this.itens == null || this.itens.isEmpty()) {
            throw OrderCannotBePlacedException.noItems(this.id());
        }
    }

    private OrderItem findOrderItem(OrderItemId orderItemId) {
        return this.itens.stream()
                .filter(i -> i.id().equals(orderItemId))
                .findFirst()
                .orElseThrow(() -> new OrderDoesNotContainOrderItemException(this.id(), orderItemId));
    }

    private void verifyIfChangeable() {
        if (!this.isDraft()) {
            throw new OrderCannotBeEditedException(this.id(), this.status());
        }
    }

    public Long version() {
        return version;
    }

    private void setVersion(Long version) {
        this.version = version;
    }

    private void setId(OrderId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setCustomerId(CustomerId customerId) {
        Objects.requireNonNull(customerId);
        this.customerId = customerId;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    private void setTotalItems(Quantity totalItens) {
        Objects.requireNonNull(totalItens);
        this.totalItens = totalItens;
    }

    private void setPlacedAt(OffsetDateTime placedAt) {
        this.placedAt = placedAt;
    }

    private void setPaidAt(OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    private void setCanceledAt(OffsetDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    private void setReadyAt(OffsetDateTime readyAt) {
        this.readyAt = readyAt;
    }

    private void setBilling(Billing billing) {
        this.billing = billing;
    }

    private void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    private void setStatus(OrderStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
    }

    private void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    private void setItems(Set<OrderItem> itens) {
        Objects.requireNonNull(itens);
        this.itens = itens;
    }

    private void setCreditCardId(CreditCardId creditCardId) {
        Objects.requireNonNull(creditCardId);
        this.creditCardId = creditCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
