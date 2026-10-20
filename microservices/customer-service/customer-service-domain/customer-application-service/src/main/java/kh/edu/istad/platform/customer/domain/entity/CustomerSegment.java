package kh.edu.istad.platform.customer.domain.entity;

import kh.edu.istad.common.domain.valueobject.SegmentId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerSegment {
    private SegmentId id;
    private String name;
    private String description;

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }
}