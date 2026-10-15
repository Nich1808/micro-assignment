package kh.edu.istad.platform.payment.persistence;

import kh.edu.istad.platform.payment.domain.CreditEntry;
import kh.edu.istad.platform.payment.domain.CreditHistory;
import kh.edu.istad.platform.payment.domain.Payment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Document(collection = "payments")
public record PaymentDocument(
        @Id String id,
        String orderId,
        String customerId,
        BigDecimal amount,
        String status,
        CreditEntryDocument creditEntry,
        List<CreditHistoryDocument> history,
        Instant createdAt
) {

    public record CreditEntryDocument(
            String id,
            String customerId,
            BigDecimal amount,
            String type
    ) {
        public static CreditEntryDocument from(CreditEntry entry) {
            return new CreditEntryDocument(
                    entry.id().toString(),
                    entry.customerId().toString(),
                    entry.amount(),
                    entry.type().name()
            );
        }

        public CreditEntry toDomain() {
            return new CreditEntry(
                    UUID.fromString(id()),
                    UUID.fromString(customerId()),
                    amount(),
                    CreditEntry.TransactionType.valueOf(type())
            );
        }
    }

    public record CreditHistoryDocument(
            String creditEntryId,
            String action,
            Instant createdAt
    ) {
        public static CreditHistoryDocument from(CreditHistory history) {
            return new CreditHistoryDocument(
                    history.creditEntryId().toString(),
                    history.action(),
                    history.createdAt()
            );
        }

        public CreditHistory toDomain() {
            return new CreditHistory(
                    UUID.fromString(creditEntryId()),
                    action(),
                    createdAt()
            );
        }
    }

    public static PaymentDocument from(Payment payment) {
        return new PaymentDocument(
                payment.getId().toString(),
                payment.getOrderId().toString(),
                payment.getCustomerId().toString(),
                payment.getAmount(),
                payment.getStatus().name(),
                CreditEntryDocument.from(payment.getCreditEntry()),
                payment.getHistory().stream().map(CreditHistoryDocument::from).toList(),
                payment.getCreatedAt()
        );
    }

    public Payment toDomain() {
        return new Payment(
                UUID.fromString(id()),
                UUID.fromString(orderId()),
                UUID.fromString(customerId()),
                amount(),
                Payment.Status.valueOf(status()),
                creditEntry().toDomain(),
                history().stream().map(CreditHistoryDocument::toDomain).toList(),
                createdAt()
        );
    }
}