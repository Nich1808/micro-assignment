package kh.edu.istad.platform.customer.restapi.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerSegmentUpdateRequest(
        @NotBlank(message = "Segment name must not be blank")
        String name,
        String description
) {}