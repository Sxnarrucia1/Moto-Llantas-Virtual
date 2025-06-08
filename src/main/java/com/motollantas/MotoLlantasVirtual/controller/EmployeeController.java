package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.ChangeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.motollantas.MotoLlantasVirtual.Service.EmployeeService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/listEmployees")
    public String listEmployees(@RequestParam(required = false) String identification,
            @RequestParam(required = false) String role,
            Model model) {
        List<Employee> employees;

        if (identification != null && !identification.isEmpty() && role != null && !role.isEmpty()) {
            employees = employeeService.getAll().stream()
                    .filter(e -> e.getIdentification().toLowerCase().contains(identification.toLowerCase()))
                    .filter(e -> e.getRoles().contains(role))
                    .toList();
        } else if (identification != null && !identification.isEmpty()) {
            employees = employeeService.findByIdentification(identification);
        } else if (role != null && !role.isEmpty()) {
            employees = employeeService.filterByRole(role);
        } else {
            employees = employeeService.getAll();
        }
        model.addAttribute("employees", employees);
        model.addAttribute("roles", employeeService.getAvailableRoles());
        model.addAttribute("selectedRole", role);
        model.addAttribute("identification", identification);
        return "employee/listEmployees";
    }

    @GetMapping("/listInactive")
    public String listInactiveEmployees(Model model) {
        List<Employee> employees = employeeService.getInactive();
        model.addAttribute("employees", employees);
        return "employee/listInactive";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute Employee employee) {
        employee.setActive(true); // Ensure the employee is active by default
        employeeService.save(employee);
        return "redirect:/employee/listEmployees";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(Model model, @PathVariable Long id, @ModelAttribute Employee updatedEmployee,
            RedirectAttributes redirectAttributes) {
        Employee existing = employeeService.getById(id);
        if (existing != null) {
            existing.setIdentification(updatedEmployee.getIdentification());
            existing.setFullName(updatedEmployee.getFullName());
            existing.setEmail(updatedEmployee.getEmail());
            existing.setAddress(updatedEmployee.getAddress());
            existing.setHiringDate(updatedEmployee.getHiringDate());
            existing.setSalary(updatedEmployee.getSalary());
            existing.setRoles(updatedEmployee.getRoles());
            redirectAttributes.addFlashAttribute("mensajeExito", "Empleado editado con éxito");
            employeeService.save(existing);
        }
        return "redirect:/employee/listEmployees";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam(required = false) String identification,
            @RequestParam(required = false) String role,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("identification", identification);
        redirectAttributes.addAttribute("role", role);
        return "redirect:/employee/listEmployees";
    }

    @PostMapping("/assign-roles")
    public String assignRoles(@RequestParam Long employeeId, @RequestParam List<String> newRoles) {
        employeeService.assignRoles(employeeId, newRoles);
        return "redirect:/employees";
    }

    // View change history of an employee
    @GetMapping("/history/{id}")
    public String viewHistory(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getById(id);
        List<ChangeHistory> history = employeeService.getChangeHistory(id);

        model.addAttribute("employee", employee);
        model.addAttribute("history", history);
        return "employees/history";
    }

    // Soft delete (mark as inactive)
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Empleado eliminado con éxito");
        return "redirect:/employee/listInactive";
    }

    // Restore employee
    @PostMapping("/restore/{id}")
    public String restoreEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.restoreEmployee(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Empleado restaurado con éxito");
        return "redirect:/employee/listEmployees";
    }
}
