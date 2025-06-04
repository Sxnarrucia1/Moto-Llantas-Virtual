package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.HistorialCambio;
import com.motollantas.MotoLlantasVirtual.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public String listarEmpleados(Model model) {
        List<Employee> empleados = empleadoService.obtenerTodos();
        List<String> roles = empleadoService.obtenerRolesDisponibles();

        model.addAttribute("empleados", empleados);
        model.addAttribute("roles", roles);
        return "empleados/index";
    }

    @GetMapping("/buscar")
    public String buscarPorCedula(@RequestParam String cedula, Model model) {
        List<Employee> empleados = empleadoService.buscarPorCedula(cedula);
        List<String> roles = empleadoService.obtenerRolesDisponibles();

        model.addAttribute("empleados", empleados);
        model.addAttribute("roles", roles);
        return "empleados/index";
    }

    @GetMapping("/filtrar")
    public String filtrarPorRol(@RequestParam String rol, Model model) {
        List<Employee> empleados = empleadoService.filtrarPorRol(rol);
        List<String> roles = empleadoService.obtenerRolesDisponibles();

        model.addAttribute("empleados", empleados);
        model.addAttribute("roles", roles);
        return "empleados/index";
    }

    @PostMapping("/asignar-roles")
    public String asignarRoles(@RequestParam Long empleadoId, @RequestParam List<String> nuevosRoles) {
        empleadoService.asignarRoles(empleadoId, nuevosRoles);
        return "redirect:/empleados";
    }

    @GetMapping("/historial/{id}")
    public String verHistorial(@PathVariable Long id, Model model) {
        Employee empleado = empleadoService.obtenerPorId(id);
        List<HistorialCambio> historial = empleadoService.obtenerHistorialDeCambios(id);

        model.addAttribute("empleado", empleado);
        model.addAttribute("historial", historial);
        return "empleados/historial";
    }
}