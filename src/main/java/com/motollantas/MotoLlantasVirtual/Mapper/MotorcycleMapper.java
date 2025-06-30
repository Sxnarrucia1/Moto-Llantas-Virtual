/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Mapper;

import com.motollantas.MotoLlantasVirtual.DTO.AdminMotorcycleDTO;
import com.motollantas.MotoLlantasVirtual.DTO.ClientMotorcycleDTO;
import com.motollantas.MotoLlantasVirtual.domain.Motorcycle;
import com.motollantas.MotoLlantasVirtual.domain.User;

/**
 *
 * @author esteb
 */
public class MotorcycleMapper {

    public static ClientMotorcycleDTO toClientDTO(Motorcycle moto) {
        ClientMotorcycleDTO dto = new ClientMotorcycleDTO();
        dto.setBrand(moto.getBrand());
        dto.setModelName(moto.getModelName());
        dto.setYear(moto.getYear());
        dto.setLicensePlate(moto.getLicensePlate());
        return dto;
    }

    public static AdminMotorcycleDTO toAdminDTO(Motorcycle moto) {
        AdminMotorcycleDTO dto = new AdminMotorcycleDTO();
        dto.setBrand(moto.getBrand());
        dto.setModelName(moto.getModelName());
        dto.setYear(moto.getYear());
        dto.setLicensePlate(moto.getLicensePlate());
        return dto;
    }

    public static Motorcycle fromClientDTO(ClientMotorcycleDTO dto, User user) {
        Motorcycle moto = new Motorcycle();
        moto.setUser(user);
        moto.setBrand(dto.getBrand());
        moto.setModelName(dto.getModelName());
        moto.setYear(dto.getYear());
        moto.setLicensePlate(dto.getLicensePlate());
        return moto;
    }

    public static Motorcycle fromAdminDTO(AdminMotorcycleDTO dto, User user) {
        Motorcycle moto = new Motorcycle();
        moto.setUser(user);
        moto.setBrand(dto.getBrand());
        moto.setModelName(dto.getModelName());
        moto.setYear(dto.getYear());
        moto.setLicensePlate(dto.getLicensePlate());
        return moto;
    }
}
