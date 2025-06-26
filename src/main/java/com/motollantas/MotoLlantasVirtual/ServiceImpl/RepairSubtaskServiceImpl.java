/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.RepairSubtaskService;
import com.motollantas.MotoLlantasVirtual.dao.RepairOrderDao;
import com.motollantas.MotoLlantasVirtual.dao.RepairSubtaskDao;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.RepairSubtask;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class RepairSubtaskServiceImpl implements RepairSubtaskService {

    @Autowired
    private RepairSubtaskDao repairSubtaskDao;

    @Autowired
    private RepairOrderDao repairOrderDao;

    @Override
    public RepairSubtask createSubtask(Long repairOrderId, String description) {
        RepairOrder order = repairOrderDao.findById(repairOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        RepairSubtask subtask = new RepairSubtask();
        subtask.setDescription(description);
        subtask.setRepairOrder(order);
        return repairSubtaskDao.save(subtask);
    }

    @Override
    public List<RepairSubtask> getSubtasksByOrderId(Long repairOrderId) {
        return repairSubtaskDao.findByRepairOrderId(repairOrderId);
    }

    @Override
    public void toggleCompletion(Long subtaskId) {
        RepairSubtask subtask = repairSubtaskDao.findById(subtaskId)
                .orElseThrow(() -> new IllegalArgumentException("Subtarea no encontrada"));
        subtask.setCompleted(!subtask.isCompleted());
        repairSubtaskDao.save(subtask);
    }

    @Override
    public void deleteSubtask(Long subtaskId) {
        repairSubtaskDao.deleteById(subtaskId);
    }
}
