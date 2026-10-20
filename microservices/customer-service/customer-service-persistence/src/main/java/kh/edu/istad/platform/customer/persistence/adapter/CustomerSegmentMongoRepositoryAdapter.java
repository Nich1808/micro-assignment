package kh.edu.istad.platform.customer.persistence.adapter;

import kh.edu.istad.common.domain.valueobject.SegmentId;
import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import kh.edu.istad.platform.customer.persistence.document.CustomerSegmentDocument;
import kh.edu.istad.platform.customer.persistence.mapper.CustomerSegmentPersistenceMapper;
import kh.edu.istad.platform.customer.persistence.repository.CustomerSegmentMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerSegmentMongoRepositoryAdapter implements CustomerSegmentRepository {

    private final CustomerSegmentMongoRepository customerSegmentMongoRepository;
    private final CustomerSegmentPersistenceMapper customerSegmentPersistenceMapper;

    @Override
    public Mono<CustomerSegment> save(CustomerSegment customerSegment) {
        CustomerSegmentDocument document = customerSegmentPersistenceMapper.toDocument(customerSegment);
        if (document == null) {
            return Mono.error(new IllegalArgumentException("CustomerSegmentDocument must not be null"));
        }

        String idStr = document.getId();
        if (idStr == null) {
            return customerSegmentMongoRepository.save(document)
                    .map(customerSegmentPersistenceMapper::toDomain);
        }

        return customerSegmentMongoRepository.existsById(idStr)
                .flatMap(exists -> {
                    document.setNewDocument(!exists);
                    return customerSegmentMongoRepository.save(document);
                })
                .map(customerSegmentPersistenceMapper::toDomain);
    }

    @Override
    public Mono<CustomerSegment> findById(SegmentId id) {
        if (id == null || id.value() == null) {
            return Mono.empty();
        }
        return customerSegmentMongoRepository.findById(id.value().toString())
                .map(doc -> {
                    doc.setNewDocument(false);
                    return customerSegmentPersistenceMapper.toDomain(doc);
                });
    }

    @Override
    public Flux<CustomerSegment> findAll() {
        return customerSegmentMongoRepository.findAll()
                .map(customerSegmentPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(SegmentId id) {
        if (id == null || id.value() == null) {
            return Mono.empty();
        }
        return customerSegmentMongoRepository.deleteById(id.value().toString());
    }
}