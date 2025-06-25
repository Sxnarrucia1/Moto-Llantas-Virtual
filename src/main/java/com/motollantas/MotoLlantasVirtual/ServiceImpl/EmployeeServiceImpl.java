package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.DTO.EmployeeDTO;
import com.motollantas.MotoLlantasVirtual.Service.EmailService;
import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.ChangeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import com.motollantas.MotoLlantasVirtual.Service.EmployeeService;
import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.dao.EmployeeDao;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.security.SecureRandom;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Suponiendo que los roles disponibles son estáticos (puedes luego cambiarlos si están en una tabla)
    private final List<String> availableRoles = Arrays.asList("ADMIN", "MECANICO", "SERVICIO_CLIENTE", "BODEGUERO");

    public String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.findAll();
    }

    @Override
    public List<Employee> findByIdentification(String identification) {
        return employeeDao.findByIdentificationContainingIgnoreCase(identification);
    }

    @Override
    public List<Employee> filterByRole(String rol) {
        return employeeDao.findByRolesContaining(rol);
    }

    @Override
    public void assignRoles(Long employeeId, List<String> newRoles) {
        Optional<Employee> optional = employeeDao.findById(employeeId);
        if (optional.isPresent()) {
            Employee employee = optional.get();
            employee.setRoles(newRoles);
            employeeDao.save(employee);

        }
    }

    @Override
    public Employee getById(Long id) {
        return employeeDao.findById(id).orElse(null);
    }

    @Override
    public List<ChangeHistory> getChangeHistory(Long employeeId) {
        // Si el historial es una tabla relacionada, deberías usar un repositorio
        // Por ahora, devolvemos una lista vacía como placeholder
        return new ArrayList<>();
    }

    @Override
    public List<String> getAvailableRoles() {
        return availableRoles;
    }

    @Override
    public List<Employee> getInactive() {
        return employeeDao.findByActiveFalse();
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeDao.findById(id).ifPresent(e -> {
            e.setActive(false);
            employeeDao.save(e);
        });
    }

    @Override
    public void restoreEmployee(Long id) {
        employeeDao.findById(id).ifPresent(e -> {
            e.setActive(true);
            employeeDao.save(e);
        });
    }

    @Override
    public void save(Employee employee) {
        employeeDao.save(employee);
    }

    @Override
    public List<Employee> getActive() {
        return employeeDao.findByActiveTrue();
    }

    @Override
    public void createEmployeeWithUser(EmployeeDTO dto) {
        if (userService.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        if (userService.existsByIdentification(dto.getIdentification())) {
            throw new RuntimeException("La identificación ya está registrada.");
        }
        
        String tempPassword = generateRandomPassword(10);

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setUserType("EMPLOYEE");
        user.setFullName(dto.getFullName());
        user.setIdentification(dto.getIdentification());
        userService.save(user);

        Employee employee = new Employee();
        employee.setFullName(dto.getFullName());
        employee.setIdentification(dto.getIdentification());
        employee.setEmail(dto.getEmail());
        employee.setAddress(dto.getAddress());
        employee.setHiringDate(dto.getHiringDate());
        employee.setSalary(dto.getSalary());
        employee.setRoles(dto.getRoles());
        employee.setUser(user);
        employee.setActive(true);
        save(employee);

        emailService.sendCredentialsEmail(user.getEmail(), tempPassword);
    }
}
