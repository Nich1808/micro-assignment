package kh.edu.istad.platform.customer.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerFilterCriteria {
    private String name;
    private String email;
    private String phoneNumber;
    private String status;
    private String segmentId;
}