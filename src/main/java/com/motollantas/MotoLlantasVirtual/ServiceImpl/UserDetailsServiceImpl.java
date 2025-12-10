package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.dao.EmployeeDao;
import com.motollantas.MotoLlantasVirtual.dao.registerDao;
import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private registerDao registerDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = registerDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<GrantedAuthority> authorities;

        Optional<Employee> employeeOpt = employeeDao.findByUser(user);
        if (employeeOpt.isPresent()) {
            // Cargar roles desde Employee
            authorities = employeeOpt.get().getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        } else {
            // Usuario normal, usar userType como rol
            authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserType()));
        }

        // Verifica si la cuenta esta inactiva
        if (!user.isStatus()) {
            throw new UsernameNotFoundException("La cuenta está inactiva");
        }

        // Verifica si esta bloqueado y si ya paso el tiempo
        if (user.getLockTime() != null) {
            boolean unlocked = userService.unlockIfTimeExpired(user);

            if (!unlocked) {
                throw new LockedException("Tu cuenta está bloqueada por múltiples intentos fallidos. Intenta más tarde.");
            }
        }


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

}
