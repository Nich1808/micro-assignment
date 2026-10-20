package kh.edu.istad.platform.customer.persistence.document;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "customers")
public class CustomerDocument implements Persistable<String> {

    @Id
    private CustomerId customerId; // 👈 រក្សា CustomerId Value Object ទុកដដែល
    private String username;
    private String familyName;
    private String givenName;
    private Email email;           // 👈 រក្សា Email Value Object ទុកដដែល
    private PhoneNumber phoneNumber;// 👈 រក្សា PhoneNumber Value Object ទុកដដែល
    private CustomerStatus status;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private Instant modifiedAt;

    @Override
    public @Nullable String getId() {
        return customerId != null ? customerId.value().toString() : null; // 👈 ផ្ដល់តម្លៃ ID ពិតប្រាកដជា String
    }

    @Override
    public boolean isNew() {
        return createdAt == null; // ឬ ឆែក customerId == null
    }
}