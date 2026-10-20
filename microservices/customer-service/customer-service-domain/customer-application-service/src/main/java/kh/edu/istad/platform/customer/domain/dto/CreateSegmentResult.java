package kh.edu.istad.platform.customer.domain.dto;

import java.util.UUID;

public record CreateSegmentResult(
        UUID id,
        String name,
        String description
) {}