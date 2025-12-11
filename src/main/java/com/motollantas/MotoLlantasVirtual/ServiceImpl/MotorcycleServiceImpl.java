/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

/**
 *
 * @author esteb
 */

import com.motollantas.MotoLlantasVirtual.Service.MotorcycleService;
import com.motollantas.MotoLlantasVirtual.dao.MotorcycleDao;
import com.motollantas.MotoLlantasVirtual.domain.Motorcycle;
import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorcycleServiceImpl implements MotorcycleService {

    private final MotorcycleDao motorcycleDao;

    public MotorcycleServiceImpl(MotorcycleDao motorcycleDao) {
        this.motorcycleDao = motorcycleDao;
    }

    @Override
    public Motorcycle save(Motorcycle motorcycle) {

        if (motorcycle.getYear() < 1900 || motorcycle.getYear() > 2100) {
            throw new IllegalArgumentException("El año debe estar entre 1900 y 2100");
        }

        if (motorcycle.getKilometraje() != null && motorcycle.getKilometraje() < 0) {
            throw new IllegalArgumentException("El kilometraje no puede ser negativo");
        }
        if (motorcycle.getDisplacement() != null && motorcycle.getDisplacement() < 0) {
            throw new IllegalArgumentException("El cilindraje no puede ser negativo");
        }

        return motorcycleDao.save(motorcycle);
    }

    @Override
    public Optional<Motorcycle> findById(Long id) {
        return motorcycleDao.findById(id);
    }

    @Override
    public Optional<Motorcycle> findByLicensePlateAndUser(String licensePlate, User owner) {
        return motorcycleDao.findByLicensePlateAndUser(licensePlate, owner);
    }

    @Override
    public List<Motorcycle> findAllByUser(User owner) {
        return motorcycleDao.findAllByUser(owner);
    }

    @Override
    public boolean existsByLicensePlate(String licensePlate) {
        return motorcycleDao.existsByLicensePlate(licensePlate);
    }

    @Override
    public void deleteById(Long id) {
        motorcycleDao.deleteById(id);
    }

    @Override
    public Optional<Motorcycle> findByLicensePlate(String licensePlate) {
        return motorcycleDao.findByLicensePlate(licensePlate);
    }

    @Override
    public List<Motorcycle> findByOwnerIdentification(String identification) {
        return motorcycleDao.findByUserIdentification(identification);
    }
}
