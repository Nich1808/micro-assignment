package kh.edu.istad.platform.business.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BusinessTest {

    @Test
    @DisplayName("Should throw exception when business email format is invalid")
    void testInvalidEmailValidation() {
        UUID uniqueId = UUID.randomUUID();
        String enterpriseName = "Tech Store";
        String malformedEmail = "invalid-email-format";
        String targetAddress = "Phnom Penh";
        Instant creationTime = Instant.now();

        assertThrows(IllegalArgumentException.class, () ->
                new Business(
                        uniqueId,
                        enterpriseName,
                        malformedEmail,
                        targetAddress,
                        Business.Status.ACTIVE,
                        creationTime
                )
        );
    }
}