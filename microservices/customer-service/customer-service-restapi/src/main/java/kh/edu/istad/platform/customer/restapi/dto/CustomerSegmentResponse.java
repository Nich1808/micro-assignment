package kh.edu.istad.platform.customer.restapi.dto;

import java.util.UUID;

public record CustomerSegmentResponse(
        UUID id,
        String name,
        String description
) {}