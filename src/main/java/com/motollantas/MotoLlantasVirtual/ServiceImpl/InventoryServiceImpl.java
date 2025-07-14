/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.InventoryService;
import com.motollantas.MotoLlantasVirtual.dao.ProductDao;
import com.motollantas.MotoLlantasVirtual.domain.Product;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrderProduct;
import com.motollantas.MotoLlantasVirtual.handler.InventoryException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ProductDao productDao;

    @Override
    public void processProductUsage(List<RepairOrderProduct> usedProducts) throws InventoryException {
        for (RepairOrderProduct usage : usedProducts) {
            // Recargar el producto desde la base de datos
            Product product = productDao.findById(usage.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            Integer currentStock = product.getStock();
            int quantityUsed = usage.getQuantityUsed();

            if (currentStock == null) {
                throw new IllegalStateException("El producto " + product.getName() + " no tiene stock definido.");
            }

            if (currentStock < quantityUsed) {
                throw new InventoryException("Falta de inventario para el producto: " + product.getName());
            }

            int newStock = currentStock - quantityUsed;
            product.setStock(newStock);
            productDao.save(product);

            if (newStock < getMinimumThreshold(product)) {
                notifyLowStock(product);
            }
        }
    }

    private int getMinimumThreshold(Product product) {
        return 5;
    }

    private void notifyLowStock(Product product) {
        System.out.println("⚠️ Disponible por debajo del límite asignado: " + product.getName());
    }
}
