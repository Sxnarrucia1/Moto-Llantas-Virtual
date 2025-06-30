/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.Motorcycle;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author esteb
 */
public interface MotorcycleService {

    Motorcycle save(Motorcycle motorcycle);

    Optional<Motorcycle> findById(Long id);

    Optional<Motorcycle> findByLicensePlateAndUser(String licensePlate, User user);

    List<Motorcycle> findAllByUser(User user);

    boolean existsByLicensePlate(String licensePlate);

    void deleteById(Long id);

    Optional<Motorcycle> findByLicensePlate(String licensePlate);

    public List<Motorcycle> findByOwnerIdentification(String identification);

}
