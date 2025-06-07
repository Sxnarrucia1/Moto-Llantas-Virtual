package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.dao.registerDao;
import com.motollantas.MotoLlantasVirtual.domain.User;
import com.motollantas.MotoLlantasVirtual.Service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class registerServiceImpl implements registerService {

    @Autowired
    private registerDao registerDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {

        if (registerDao.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        if (registerDao.existsByIdentification(user.getIdentification())) {
            throw new RuntimeException("La identificación ya está registrada.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return registerDao.save(user);
    }

}
