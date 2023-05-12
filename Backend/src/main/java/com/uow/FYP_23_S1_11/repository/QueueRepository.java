package com.uow.FYP_23_S1_11.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uow.FYP_23_S1_11.domain.Queue;

public interface QueueRepository extends JpaRepository<Queue, Integer> {
    List<Queue> findByQueueId(Integer queueId);

    @Query(value = "SELECT count(q.queue_number) FROM queue q INNER JOIN appointments a ON q.appointment_id = a.appointment_id INNER JOIN clinic c ON a.clinic_id = c.clinic_id WHERE q.queue_number < ?1 AND q.status = 'WAITING_IN_QUEUE' AND c.clinic_name = ?2", nativeQuery = true)
    Integer findCountByQueueNumber(Integer queueNumber, String clinicName);

    @Query(value = "SELECT q.queue_id,q.date,q.priority,q.queue_number,q.status,q.time,q.appointment_id FROM queue q WHERE q.queue_number = ?1 AND q.status = 'WAITING_IN_QUEUE'", nativeQuery = true)
    Optional<Queue> findbyQueueNumber(Integer queueNumber);

    @Query(value = "SELECT q.queue_number FROM queue q INNER JOIN appointments a ON q.appointment_id = a.appointment_id INNER JOIN clinic c ON a.clinic_id = c.clinic_id WHERE q.queue_number < ?1 AND c.clinic_name = ?2", nativeQuery = true)
    Integer findByQueueNumber(Integer queueNumber, String clinicName);
}
