package kh.edu.istad.platform.customer.restapi.mapper;

import kh.edu.istad.platform.customer.domain.dto.CreateSegmentCommand;
import kh.edu.istad.platform.customer.domain.dto.CreateSegmentResult;
import kh.edu.istad.platform.customer.domain.dto.UpdateSegmentCommand;
import kh.edu.istad.platform.customer.domain.dto.UpdateSegmentResult;
import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.restapi.dto.CustomerSegmentCreateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerSegmentResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerSegmentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerSegmentWebMapper {

    CreateSegmentCommand toCommand(CustomerSegmentCreateRequest request);

    @Mapping(target = "segmentId", source = "id")
    UpdateSegmentCommand toCommand(UUID id, CustomerSegmentUpdateRequest request);

    @Mapping(target = "id", source = "id.value")
    CustomerSegmentResponse toResponse(CustomerSegment customerSegment);

    CustomerSegmentResponse toResponse(CreateSegmentResult result);

    CustomerSegmentResponse toResponse(UpdateSegmentResult result);
}