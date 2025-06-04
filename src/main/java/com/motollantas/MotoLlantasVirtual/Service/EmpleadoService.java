
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.HistorialCambio;

import java.util.List;

public interface EmpleadoService {

    List<Employee> obtenerTodos();

    List<Employee> buscarPorCedula(String cedula);

    List<Employee> filtrarPorRol(String rol);

    void asignarRoles(Long empleadoId, List<String> nuevosRoles);

    Employee obtenerPorId(Long id);

    List<HistorialCambio> obtenerHistorialDeCambios(Long empleadoId);

    List<String> obtenerRolesDisponibles();
}
