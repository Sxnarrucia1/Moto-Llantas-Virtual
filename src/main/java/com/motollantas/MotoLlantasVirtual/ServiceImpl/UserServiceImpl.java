package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDao.findByEmail(email).orElse(null);
    }
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        userDao.deleteByEmail(email);
    }

}