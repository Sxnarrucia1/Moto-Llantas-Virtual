/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.Feedback;
import java.util.Optional;

/**
 *
 * @author esteb
 */
public interface FeedbackService {

    boolean existsByRepairOrderId(Long repairOrderId);

    Feedback saveFeedback(Feedback feedback);

    Optional<Feedback> findByRepairOrderId(Long repairOrderId);
}
