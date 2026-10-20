package kh.edu.istad.platform.customer.persistence.adapter;


import kh.edu.istad.platform.customer.domain.dto.CustomerStatisticsResponse;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import kh.edu.istad.platform.customer.domain.dto.CustomerFilterCriteria;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerCustomRepository;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import kh.edu.istad.platform.customer.persistence.mapper.CustomerPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerCustomRepositoryImpl implements CustomerCustomRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final CustomerPersistenceMapper customerPersistenceMapper;


    @Override
    public Flux<CustomerStatisticsResponse> getCustomerStatistics() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("status", "segmentId")
                        .count().as("totalCustomers"),
                Aggregation.project("totalCustomers")
                        .and("_id.status").as("status")
                        .and("_id.segmentId").as("segmentId")
        );

        return reactiveMongoTemplate.aggregate(
                aggregation,
                CustomerDocument.class,
                CustomerStatisticsResponse.class
        );
    }

    @Override
    public Flux<Customer> findByCriteria(CustomerFilterCriteria criteria) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (criteria.getName() != null && !criteria.getName().isBlank()) {
            criteriaList.add(Criteria.where("name").regex(criteria.getName(), "i"));
        }
        if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
            criteriaList.add(Criteria.where("email").regex(criteria.getEmail(), "i"));
        }
        if (criteria.getPhoneNumber() != null && !criteria.getPhoneNumber().isBlank()) {
            criteriaList.add(Criteria.where("phoneNumber").regex(criteria.getPhoneNumber(), "i"));
        }
        if (criteria.getStatus() != null && !criteria.getStatus().isBlank()) {
            criteriaList.add(Criteria.where("status").is(criteria.getStatus()));
        }
        if (criteria.getSegmentId() != null && !criteria.getSegmentId().isBlank()) {
            criteriaList.add(Criteria.where("segmentId").is(criteria.getSegmentId()));
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        return reactiveMongoTemplate.find(query, CustomerDocument.class)
                .map(customerPersistenceMapper::toDomain);
    }

}