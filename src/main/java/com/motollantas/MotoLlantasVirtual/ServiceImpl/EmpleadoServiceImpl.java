
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.HistorialCambio;
import com.motollantas.MotoLlantasVirtual.dao.EmpleadoRepository;
import com.motollantas.MotoLlantasVirtual.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Suponiendo que los roles disponibles son estáticos (puedes luego cambiarlos si están en una tabla)
    private final List<String> rolesDisponibles = Arrays.asList("ADMIN", "MECANICO", "VENDEDOR");

    @Override
    public List<Employee> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    @Override
    public List<Employee> buscarPorCedula(String cedula) {
        return empleadoRepository.findByCedulaContainingIgnoreCase(cedula);
    }

    @Override
    public List<Employee> filtrarPorRol(String rol) {
        return empleadoRepository.findByRolesContaining(rol);
    }

    @Override
    public void asignarRoles(Long empleadoId, List<String> nuevosRoles) {
        Optional<Employee> optional = empleadoRepository.findById(empleadoId);
        if (optional.isPresent()) {
            Employee empleado = optional.get();
            empleado.setRoles(nuevosRoles);
            empleadoRepository.save(empleado);

        }
    }

    @Override
    public Employee obtenerPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public List<HistorialCambio> obtenerHistorialDeCambios(Long empleadoId) {
        // Si el historial es una tabla relacionada, deberías usar un repositorio
        // Por ahora, devolvemos una lista vacía como placeholder
        return new ArrayList<>();
    }

    @Override
    public List<String> obtenerRolesDisponibles() {
        return rolesDisponibles;
    }
}

