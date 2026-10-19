package kh.edu.istad.platform.customer.persistence.document;

import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "customers")
public class CustomerDocument implements Persistable<String> {

    @Id
    private String customerId;

    private String username;
    private String familyName;
    private String givenName;
    private String email;
    private String phoneNumber;
    private CustomerStatus status;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private Instant modifiedAt; // គួរតែប្រើ Instant ឬ LocalDateTime ជំនួស String

    @Override
    public @Nullable String getId() {
        return customerId; // ត្រូវ Return តម្លៃ customerId ទីនេះ
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}