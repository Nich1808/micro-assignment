package kh.edu.istad.platform.order.persistence;

import kh.edu.istad.platform.order.application.OrderRepository;

import kh.edu.istad.platform.order.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderJpaRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderJpaRepository springDataOrderJpaRepository;

    @Override
    public Order save(Order domainOrder) {
        log.info("Persisting order aggregate with identifier: {}", domainOrder.id());

        OrderJpaEntity persistenceEntity = OrderJpaEntity.from(domainOrder);
        springDataOrderJpaRepository.save(persistenceEntity);

        return domainOrder;
    }
}