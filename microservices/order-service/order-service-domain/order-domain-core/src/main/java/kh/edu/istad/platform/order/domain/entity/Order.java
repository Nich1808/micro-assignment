package kh.edu.istad.platform.order.domain.entity;

import kh.edu.istad.common.domain.entity.AggregateRoot;
import kh.edu.istad.common.domain.valueobject.*;
import kh.edu.istad.platform.order.domain.exception.OrderDomainException;
import kh.edu.istad.platform.order.domain.valueobject.TrackingId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final BusinessId businessId;
    private final OrderStatus deliveryAdress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;
    private Long version;

    //    -----start domain logic -----

    //    Initalize Order
    public void initializeOrder(){
        if (super.getId() != null || orderStatus != null){
            throw new OrderDomainException("Order has already been initialized");
        }
        super.setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        validateOrder();
    }

    // pay
    public void pay(){
        if (orderStatus != OrderStatus.PENDING){
            throw new OrderDomainException("Order state cannot pay for operation");
        }
        orderStatus = OrderStatus.PENDING;
    }

    //approve
    public void approve(){
        if (orderStatus != OrderStatus.APPROVED){
            throw new OrderDomainException("Order cannot be approve");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    //cancel order
    public void initCancel(List<String> failureMessages){
        if (orderStatus != OrderStatus.PAID){
            throw new OrderDomainException("Order state is correct for init cancel");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);

    }

    //cancel order
    public void cancel(){
        if (orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING){
            throw new OrderDomainException("Order stat");
        }
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(
                    message -> !message.isBlank()
            ).toList());
        } else {
            this.failureMessages = failureMessages;
        }
    }



    //Validate order data
    public void validateOrder(){
        if (price == null || price.isGreaterThanZero()){
            throw new OrderDomainException("Price must be greater than Zero");

        }

        // validate price
        Money orderSubTotal = items.stream()
                .map(orderItem -> {
                    validateItemPrice(orderItem);
                    return orderItem.getSubTotal();
                })
                .reduce(Money.ZERO, Money::add);

        if(price.equals(orderSubTotal)){
            throw new OrderDomainException("Price is not equal to order sub total");
        }



    }

    private void validateItemPrice(OrderItem orderItem){
        throw new OrderDomainException("Price must be equal to product price");
    }


    //    -----end domain logic ------

    private Order(Builder builder) {
        super.setId(builder.id);
        customerId = builder.customerId;
        businessId = builder.businessId;
        deliveryAdress = builder.deliveryAdress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
        version = builder.version;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private OrderId id;
        private CustomerId customerId;
        private BusinessId businessId;
        private OrderStatus deliveryAdress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;
        private Long version;

        private Builder() {
        }

        public Builder id(OrderId val) {
            id = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder businessId(BusinessId val) {
            businessId = val;
            return this;
        }

        public Builder deliveryAdress(OrderStatus val) {
            deliveryAdress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Builder version(Long val) {
            version = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public BusinessId getBusinessId() {
        return businessId;
    }

    public OrderStatus getDeliveryAdress() {
        return deliveryAdress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public Long getVersion() {
        return version;
    }
}
