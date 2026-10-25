package kh.edu.istad.platform.order.domain.entity;

import kh.edu.istad.common.domain.entity.AggregateRoot;
import kh.edu.istad.common.domain.valueobject.BusinessId;

import java.util.List;

public class Business extends AggregateRoot<BusinessId> {
    private final List<Product> product;
    private Boolean active;

    private Business(Builder builder) {
        super.setId(builder.id);
        product = builder.product;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private BusinessId id;
        private List<Product> product;
        private Boolean active;

        private Builder() {
        }

        public Builder id(BusinessId val) {
            id = val;
            return this;
        }

        public Builder product(List<Product> val) {
            product = val;
            return this;
        }

        public Builder active(Boolean val) {
            active = val;
            return this;
        }

        public Business build() {
            return new Business(this);
        }
    }

    public List<Product> getProduct() {
        return product;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
