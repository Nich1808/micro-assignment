package kh.edu.istad.platform.business.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class Business {

    public enum Status {
        ACTIVE, SUSPENDED
    }

    private final UUID id;
    private final String name;
    private final String email;
    private final String address;
    private final Status status;
    private final Instant createdAt;

    public Business(UUID identifier, String businessName, String contactEmail,
                    String physicalAddress, Status accountStatus, Instant timestamp) {

        this.id = Objects.requireNonNull(identifier, "Business identifier cannot be null");

        if (businessName == null || businessName.trim().isEmpty()) {
            throw new IllegalArgumentException("Business name must not be blank");
        }
        this.name = businessName.trim();

        if (contactEmail == null || !contactEmail.contains("@") || contactEmail.indexOf('@') >= contactEmail.lastIndexOf('.')) {
            throw new IllegalArgumentException("A valid email address is required");
        }
        this.email = contactEmail.trim();

        if (physicalAddress == null || physicalAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Physical address cannot be empty");
        }
        this.address = physicalAddress.trim();

        this.status = Objects.requireNonNull(accountStatus, "Business status is required");
        this.createdAt = Objects.requireNonNull(timestamp, "Creation timestamp is mandatory");
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String address() {
        return address;
    }

    public Status status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }
}