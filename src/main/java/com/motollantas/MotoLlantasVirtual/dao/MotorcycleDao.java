/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.dao;

/**
 *
 * @author esteb
 */
import com.motollantas.MotoLlantasVirtual.domain.Motorcycle;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleDao extends JpaRepository<Motorcycle, Long> {

    Optional<Motorcycle> findByLicensePlateAndUser(String licensePlate, User user);

    List<Motorcycle> findAllByUser(User user);

    boolean existsByLicensePlate(String licensePlate);

    Optional<Motorcycle> findByLicensePlate(String licensePlate);

    boolean existsByLicensePlateIgnoreCase(String licensePlate);

    List<Motorcycle> findByUserIdentification(String identification);

}
