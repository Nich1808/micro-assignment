package kh.edu.istad.platform.customer.domain.dto;

public record CreateSegmentCommand(
        String name,
        String description
) {}