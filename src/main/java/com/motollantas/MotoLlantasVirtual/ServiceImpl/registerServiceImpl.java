package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Dao.registerDao;
import com.motollantas.MotoLlantasVirtual.Domain.User;
import com.motollantas.MotoLlantasVirtual.Service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class registerServiceImpl implements registerService {

    @Autowired
    private registerDao registerDao;

    @Override
    public User register(User user) {

        if (registerDao.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        return registerDao.save(user);
    }



}
