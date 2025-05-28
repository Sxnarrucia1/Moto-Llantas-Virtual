package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface registerService {

    public User register(User user);

}