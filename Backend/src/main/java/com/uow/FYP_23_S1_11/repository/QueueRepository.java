package com.uow.FYP_23_S1_11.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uow.FYP_23_S1_11.domain.Queue;

public interface QueueRepository extends JpaRepository<Queue, Integer> {
    List<Queue> findByQueueId(Integer queueId);
}
