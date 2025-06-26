/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.RepairSubtask;
import java.util.List;

/**
 *
 * @author esteb
 */
public interface RepairSubtaskService {

    RepairSubtask createSubtask(Long repairOrderId, String description);

    List<RepairSubtask> getSubtasksByOrderId(Long repairOrderId);

    void toggleCompletion(Long subtaskId);

    void deleteSubtask(Long subtaskId);
}
