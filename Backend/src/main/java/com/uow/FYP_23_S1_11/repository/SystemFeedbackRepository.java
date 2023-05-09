package com.uow.FYP_23_S1_11.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uow.FYP_23_S1_11.domain.SystemFeedback;
import com.uow.FYP_23_S1_11.domain.UserAccount;

public interface SystemFeedbackRepository extends JpaRepository<SystemFeedback, Integer> {
    public Page<?> findByAccountFeedback(UserAccount accountFeedback, Pageable pageable);
}
