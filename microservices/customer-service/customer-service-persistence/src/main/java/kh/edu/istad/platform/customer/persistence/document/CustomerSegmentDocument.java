package kh.edu.istad.platform.customer.persistence.document;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "customer_segments")
public class CustomerSegmentDocument implements Persistable<String> {

    @Id
    private String id;
    private String name;
    private String description;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private Instant modifiedAt;

    @Transient
    @Builder.Default
    private boolean newDocument = true;

    @Override
    public boolean isNew() {
        return this.newDocument;
    }
}