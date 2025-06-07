/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.DTO.UserDTO;
import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author esteb
 */
@RestController
@RequestMapping("/api/users")
public class UserControllerAPI {

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<UserDTO> searchByIdentification(@RequestParam String identification) {
        Optional<User> userOpt = userService.findByIdentification(identification);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserDTO dto = new UserDTO(user.getFullName(), user.getIdentification());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
