/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.RepairOrderProduct;
import com.motollantas.MotoLlantasVirtual.handler.InventoryException;
import java.util.List;

/**
 *
 * @author esteb
 */
public interface InventoryService {

    void processProductUsage(List<RepairOrderProduct> usedProducts) throws InventoryException;
}
