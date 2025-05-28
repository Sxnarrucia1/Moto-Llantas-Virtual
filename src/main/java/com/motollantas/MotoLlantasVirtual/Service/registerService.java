package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.Domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface registerService {

    public User register(User user);

}
