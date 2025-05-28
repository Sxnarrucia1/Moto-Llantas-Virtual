/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.dao.RepairOrderDao;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author esteb
 */
public class RepairOrderServiceImpl implements RepairOrderService{
    
    @Autowired
    RepairOrderDao repair;

    @Override
    public void Save(RepairOrder repairOrder) {
        repair.save(repairOrder);
    }
    
}
