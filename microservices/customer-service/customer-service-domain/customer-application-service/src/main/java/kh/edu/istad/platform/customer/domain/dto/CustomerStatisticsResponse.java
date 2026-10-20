package kh.edu.istad.platform.customer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatisticsResponse {
    private String status;
    private String segmentId;
    private long totalCustomers;
}