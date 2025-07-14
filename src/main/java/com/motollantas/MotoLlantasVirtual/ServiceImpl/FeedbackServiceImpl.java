/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.FeedbackService;
import com.motollantas.MotoLlantasVirtual.dao.FeedbackDao;
import com.motollantas.MotoLlantasVirtual.domain.Feedback;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackDao feedbackDao;

    @Override
    public boolean existsByRepairOrderId(Long repairOrderId) {
        return feedbackDao.findByRepairOrderId(repairOrderId).isPresent();
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackDao.save(feedback);
    }

    @Override
    public Optional<Feedback> findByRepairOrderId(Long repairOrderId) {
        return feedbackDao.findByRepairOrderId(repairOrderId);
    }
}
