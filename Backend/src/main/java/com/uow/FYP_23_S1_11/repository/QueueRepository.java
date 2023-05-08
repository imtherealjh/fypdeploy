package com.uow.FYP_23_S1_11.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uow.FYP_23_S1_11.domain.Queue;

public interface QueueRepository extends JpaRepository<Queue, Integer> {
    List<Queue> findByQueueId(Integer queueId);

    @Query(value = "SELECT count(q.queue_number) FROM queue q WHERE q.queue_number < ?1 AND q.status = 'WAITING_IN_QUEUE'", nativeQuery = true)
    Integer findCountByQueueNumber(Integer queueNumber);

    // Optional<Queue> findbyQueueNumber(Integer queueNumber);
}
