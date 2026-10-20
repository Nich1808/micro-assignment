package kh.edu.istad.common.domain.valueobject;

import java.util.UUID;

public record SegmentId(UUID value) {
    public SegmentId {
        if (value == null) {
            throw new IllegalArgumentException("SegmentId must not be null");
        }
    }

    public static SegmentId random() {
        return new SegmentId(UUID.randomUUID());
    }
}