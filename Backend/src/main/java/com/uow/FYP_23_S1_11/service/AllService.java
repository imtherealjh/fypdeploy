package com.uow.FYP_23_S1_11.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.uow.FYP_23_S1_11.domain.request.FeedbackRequest;

public interface AllService {
    public Map<?, ?> getAllSystemFeedback(Pageable pagable);

    public Boolean insertSystemFeedback(FeedbackRequest feedbackReq);

    public Boolean updateSystemFeedback(Integer systemFeedbackId,
            FeedbackRequest feedbackReq);

    public Boolean deleteSystemFeedback(Integer systemFeedbackId);
}
