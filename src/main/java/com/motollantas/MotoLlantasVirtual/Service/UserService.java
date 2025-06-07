package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.User;

public interface UserService {
    User getCurrentUser();
    User findByEmail(String email);
    void save(User user);
    void deleteByEmail(String email);
}
