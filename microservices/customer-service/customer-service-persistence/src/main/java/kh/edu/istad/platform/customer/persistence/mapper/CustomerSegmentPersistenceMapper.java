package kh.edu.istad.platform.customer.persistence.mapper;

import kh.edu.istad.common.domain.valueobject.SegmentId;
import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.persistence.document.CustomerSegmentDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {SegmentId.class, UUID.class})
public interface CustomerSegmentPersistenceMapper {

    @Mapping(target = "id", expression = "java(customerSegment.getId() != null ? customerSegment.getId().value().toString() : null)")
    @Mapping(target = "newDocument", ignore = true)
    CustomerSegmentDocument toDocument(CustomerSegment customerSegment);

    @Mapping(target = "id", expression = "java(customerSegmentDocument.getId() != null ? new SegmentId(UUID.fromString(customerSegmentDocument.getId().toString())) : null)")
    CustomerSegment toDomain(CustomerSegmentDocument customerSegmentDocument);
}