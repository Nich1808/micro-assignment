package kh.edu.istad.platform.customer.domain.dto;

import java.util.UUID;

public record UpdateSegmentCommand(
        UUID segmentId,
        String name,
        String description
) {}