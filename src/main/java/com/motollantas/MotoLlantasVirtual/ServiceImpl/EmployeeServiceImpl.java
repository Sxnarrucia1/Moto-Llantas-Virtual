package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.ChangeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import com.motollantas.MotoLlantasVirtual.Service.EmployeeService;
import com.motollantas.MotoLlantasVirtual.dao.EmployeeDao;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    // Suponiendo que los roles disponibles son estáticos (puedes luego cambiarlos si están en una tabla)
    private final List<String> availableRoles = Arrays.asList("ADMIN", "MECANICO", "SERVICIO_CLIENTE", "BODEGUERO");

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

}
