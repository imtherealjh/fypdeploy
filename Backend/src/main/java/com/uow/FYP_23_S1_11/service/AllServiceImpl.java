package com.uow.FYP_23_S1_11.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.SystemFeedback;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.FeedbackRequest;
import com.uow.FYP_23_S1_11.enums.ERole;
import com.uow.FYP_23_S1_11.repository.SystemFeedbackRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AllServiceImpl implements AllService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SystemFeedbackRepository systemFeedbackRepo;

    @Override
    public Map<?, ?> getAllSystemFeedback(Pageable pageable) {
        UserAccount user = Constants.getAuthenticatedUser();

        String hsql = "SELECT sf FROM SystemFeedback sf ";
        hsql += user.getRole() != ERole.SYSTEM_ADMIN ? "WHERE sf.accountFeedback = :account " : "";
        hsql += "GROUP BY sf.systemFeedbackId";

        TypedQuery<SystemFeedback> query = entityManager.createQuery(
                hsql,
                SystemFeedback.class);
        if (user.getRole() != ERole.SYSTEM_ADMIN) {
            query.setParameter("account", user);
        }

        List<Map<String, Object>> retrievedFeedbacks = query
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .map(obj -> {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", obj.getSystemFeedbackId());
                    if (user.getRole() == ERole.SYSTEM_ADMIN) {
                        var account = obj.getAccountFeedback();
                        var clinic = obj.getSystemFeedbackClinic();

                        TypedQuery<String> userQuery = entityManager.createNamedQuery("findNameInTables", String.class);
                        userQuery.setParameter("account", account);
                        var name = userQuery.getSingleResult();

                        map.put("name", name);
                        map.put("clinic", clinic != null ? clinic.getClinicName() : "-");
                    }
                    map.put("datetime", obj.getLocalDateTime());
                    map.put("feedback", obj.getFeedback());
                    map.put("status", obj.getStatus());
                    return map;
                })
                .collect(Collectors.toList());

        Page<?> page = PageableExecutionUtils.getPage(retrievedFeedbacks, pageable, () -> query.getResultList().size());
        return Constants.convertToResponse(page);
    }

    @Override
    public Boolean insertSystemFeedback(FeedbackRequest feedbackReq) {
        try {
            SystemFeedback systemFeedback = new SystemFeedback();
            UserAccount user = Constants.getAuthenticatedUser();

            if (user == null) {
                throw new IllegalArgumentException("Unknown user...");
            }

            Clinic clinic = Optional.ofNullable(user.getClinic())
                    .orElseGet(() -> Optional.ofNullable(user.getDoctor())
                            .map(elem -> elem.getDoctorClinic())
                            .orElseGet(() -> Optional.ofNullable(user.getNurse())
                                    .map(elem -> elem.getNurseClinic())
                                    .orElseGet(() -> Optional
                                            .ofNullable(user.getFrontDesk())
                                            .map(elem -> elem.getFrontDeskClinic())
                                            .orElse(null))));

            systemFeedback.setStatus("UNSOLVED");
            systemFeedback.setFeedback(feedbackReq.getFeedback());
            systemFeedback.setAccountFeedback(user);
            systemFeedback.setSystemFeedbackClinic(clinic);
            systemFeedbackRepo.save(systemFeedback);
            return true;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateSystemFeedback(Integer systemFeedbackId,
            FeedbackRequest feedbackReq) {
        UserAccount user = Constants.getAuthenticatedUser();
        Optional<SystemFeedback> systemFeedbackOptional = systemFeedbackRepo
                .findById(systemFeedbackId);

        if (systemFeedbackOptional.isEmpty()
                || !user.getAccountId().equals(systemFeedbackOptional.get()
                        .getAccountFeedback().getAccountId())) {
            throw new IllegalArgumentException("System feedback does not exist...");
        }

        SystemFeedback systemFeedback = systemFeedbackOptional.get();
        if (LocalDateTime.now().isAfter(systemFeedback.getLocalDateTime().plusDays(1))) {
            throw new IllegalArgumentException("System feedback has exceeded a day...");
        }

        systemFeedback.setFeedback(feedbackReq.getFeedback());
        systemFeedbackRepo.save(systemFeedback);
        return true;
    }

    @Override
    public Boolean deleteSystemFeedback(Integer systemFeedbackId) {
        UserAccount user = Constants.getAuthenticatedUser();
        Optional<SystemFeedback> systemFeedbackOptional = systemFeedbackRepo
                .findById(systemFeedbackId);
        if (systemFeedbackOptional.isEmpty()
                || !user.getAccountId().equals(systemFeedbackOptional.get()
                        .getAccountFeedback().getAccountId())) {
            throw new IllegalArgumentException("System feedback does not exist...");
        }

        SystemFeedback systemFeedback = systemFeedbackOptional.get();
        if (LocalDateTime.now().isAfter(systemFeedback.getLocalDateTime().plusDays(1))) {
            throw new IllegalArgumentException("System feedback has exceeded a day...");
        }
        systemFeedbackRepo.delete(systemFeedback);
        return true;
    }

}
