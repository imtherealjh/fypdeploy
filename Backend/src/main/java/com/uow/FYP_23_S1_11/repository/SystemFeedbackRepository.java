package com.uow.FYP_23_S1_11.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uow.FYP_23_S1_11.domain.SystemFeedback;

public interface SystemFeedbackRepository extends JpaRepository<SystemFeedback, Integer> {
    List<SystemFeedback> findBySystemFeedbackId(Integer systemFeedbackId);

    @Query(value = "SELECT fb.system_feedback_id,fb.ratings,fb.feedback,fb.date,fb.account_type,fb.account_id,fb.status,fb.time,fb.clinic_name FROM system_feedback fb WHERE fb.account_type = ?1 AND fb.status = 'UNSOLVED' ORDER BY fb.date,fb.account_id", nativeQuery = true)
    public List<SystemFeedback> findByRole(String role);

    @Query(value = "SELECT fb.system_feedback_id,fb.ratings,fb.feedback,fb.date,fb.account_type,fb.account_id,fb.status,fb.time,fb.clinic_name FROM system_feedback fb WHERE fb.date BETWEEN ?1 AND ?2 AND fb.status = 'UNSOLVED' ORDER BY fb.date,fb.account_id", nativeQuery = true)
    public List<SystemFeedback> findByDate(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT fb.system_feedback_id,fb.ratings,fb.feedback,fb.date,fb.account_type,fb.account_id,fb.status,fb.time,fb.clinic_name FROM system_feedback fb WHERE fb.date BETWEEN ?1 AND ?2 AND fb.account_type = ?3 AND fb.status = 'UNSOLVED' ORDER BY fb.date,fb.account_id", nativeQuery = true)
    public List<SystemFeedback> findByDateAndRole(LocalDate startDate, LocalDate endDate, String role);

    @Query(value = "SELECT fb.system_feedback_id,fb.ratings,fb.feedback,fb.date,fb.account_type,fb.account_id,fb.status,fb.time,fb.clinic_name FROM system_feedback fb WHERE fb.status = 'UNSOLVED' ORDER BY fb.date,fb.account_id", nativeQuery = true)
    public List<SystemFeedback> findByPendingStatus();

    @Query(value = "SELECT fb.system_feedback_id,fb.ratings,fb.feedback,fb.date,fb.account_type,fb.account_id,fb.status,fb.time,fb.clinic_name FROM system_feedback fb WHERE fb.status = 'COMPLETED' ORDER BY fb.date,fb.account_id", nativeQuery = true)
    public List<SystemFeedback> findByCompleteStatus();

}
