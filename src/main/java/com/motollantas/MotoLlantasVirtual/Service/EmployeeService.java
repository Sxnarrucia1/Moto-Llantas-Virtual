package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.DTO.EmployeeDTO;
import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.ChangeHistory;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();

    List<Employee> findByIdentification(String identification);

    List<Employee> filterByRole(String role);

    void assignRoles(Long employeeId, List<String> nuevosRoles);

    Employee getById(Long id);

    List<ChangeHistory> getChangeHistory(Long empleadoId);

    List<String> getAvailableRoles();

    List<Employee> getInactive();

    List<Employee> getActive();

    void deleteEmployee(Long id);

    void restoreEmployee(Long id);

    void save(Employee employee);
    
    public void createEmployeeWithUser(EmployeeDTO dto);

}
