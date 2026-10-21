package kh.edu.istad.platform.customer.persistence.adapter;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import kh.edu.istad.platform.customer.persistence.mapper.CustomerPersistenceMapper;
import kh.edu.istad.platform.customer.persistence.repository.CustomerMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerMongoRepositoryAdapter implements CustomerRepository {

    private final CustomerMongoRepository customerMongoRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    @Override
    public Mono<Customer> save(Customer customer) {
        CustomerDocument customerDocument = customerPersistenceMapper.toDocument(customer);
        if (customerDocument == null) {
            return Mono.error(new IllegalArgumentException("CustomerDocument must not be null"));
        }

        String idStr = customerDocument.getId();
        if (idStr == null) {
            return customerMongoRepository.save(customerDocument)
                    .map(customerPersistenceMapper::toDomain);
        }


        return customerMongoRepository.existsById(idStr)
                .flatMap(exists -> {
                    customerDocument.setNewDocument(!exists);
                    return customerMongoRepository.save(customerDocument);
                })
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Customer> findById(CustomerId id) {
        if (id == null || id.value() == null) {
            return Mono.empty();
        }
        return customerMongoRepository.findById(id.value().toString())
                .map(doc -> {
                    doc.setNewDocument(false); // ទាញចេញពី DB មកត្រូវតែជា Update
                    return customerPersistenceMapper.toDomain(doc);
                });
    }

    @Override
    public Flux<Customer> findAll() {
        return customerMongoRepository.findAll()
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(CustomerId id) {
        if (id == null || id.value() == null) {
            return Mono.empty();
        }
        return customerMongoRepository.deleteById(id.value().toString());
    }
}