/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.DTO.AdminDateDTO;
import com.motollantas.MotoLlantasVirtual.DTO.ClientDateDTO;
import com.motollantas.MotoLlantasVirtual.Service.InventoryService;
import com.motollantas.MotoLlantasVirtual.Service.MotorcycleService;
import com.motollantas.MotoLlantasVirtual.Service.NotificationService;
import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.Service.ServiceTypeService;
import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.dao.RepairOrderDao;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.Motorcycle;
import com.motollantas.MotoLlantasVirtual.domain.OrderPriority;
import com.motollantas.MotoLlantasVirtual.domain.OrderStatus;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrderProduct;
import com.motollantas.MotoLlantasVirtual.domain.ServiceType;
import com.motollantas.MotoLlantasVirtual.domain.User;
import com.motollantas.MotoLlantasVirtual.handler.InventoryException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    RepairOrderDao repair;

    @Autowired
    UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ServiceTypeService serviceTypeService;

    @Autowired
    UserService userService;

    @Autowired
    MotorcycleService motorcycleService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    NotificationService notificationService;

    @Override
    public void createDateClient(ClientDateDTO dto) {

        User user = userService.findByIdentification(dto.getIdentification())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Optional<Motorcycle> motorcycleOpt = motorcycleService.findByLicensePlateAndUser(dto.getLicensePlate(), user);

        Motorcycle motorcycle = motorcycleOpt.orElseGet(() -> {
            Motorcycle newMoto = new Motorcycle();
            newMoto.setUser(user);
            newMoto.setBrand(dto.getBrand());
            newMoto.setModelName(dto.getModelName());
            newMoto.setYear(dto.getYear());
            newMoto.setLicensePlate(dto.getLicensePlate());
            return motorcycleService.save(newMoto);
        });

        ServiceType serviceType = serviceTypeService.findById(dto.getServiceTypeId());

        RepairOrder order = new RepairOrder();
        order.setUser(user);
        order.setFullName(user.getFullName());
        order.setIdentification(user.getIdentification());
        order.setMotorcycle(motorcycle);
        order.setAppointmentDate(dto.getAppointmentDate());
        order.setServiceType(serviceType);
        order.setOrderStatus(OrderStatus.NUEVO);
        order.setPriority(OrderPriority.BAJA);

        if (order.getUser() == null) {
            throw new IllegalStateException("La orden no tiene un usuario asignado");
        }

        repair.save(order);
    }

    @Override
    public boolean existsByAppointmentDate(LocalDateTime dateTime) {
        return repair.existsByAppointmentDate(dateTime);
    }

    @Override
    public boolean hasOverlappingAppointment(LocalDateTime newStart, ServiceType serviceType) {
        LocalDateTime newEnd = newStart.plus(serviceType.getDuration());

        LocalDateTime startOfDay = newStart.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = newStart.toLocalDate().atTime(LocalTime.MAX);

        List<RepairOrder> appointments = repair.findAppointmentsInDay(startOfDay, endOfDay);

        for (RepairOrder r : appointments) {
            LocalDateTime existingStart = r.getAppointmentDate();
            LocalDateTime existingEnd = existingStart.plus(r.getServiceType().getDuration());

            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<ClientDateDTO> getAppointmentbyUser(Long userId) {
        List<RepairOrder> orders = repair.findByUserId(userId);
        return orders.stream().map(order -> {
            ClientDateDTO dto = modelMapper.map(order, ClientDateDTO.class);
            if (order.getServiceType() != null) {
                dto.setServiceTypeName(order.getServiceType().getServiceName());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<RepairOrder> findById(Long id) {
        return repair.findById(id);
    }

    @Override
    public void updateDateClient(ClientDateDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User client = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        RepairOrder orden = repair.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        ServiceType serviceType = serviceTypeService.findById(dto.getServiceTypeId());

        Optional<Motorcycle> motorcycleOpt = motorcycleService.findByLicensePlateAndUser(dto.getLicensePlate(), client);

        Motorcycle motorcycle = motorcycleOpt.orElseGet(() -> {
            Motorcycle newMoto = new Motorcycle();
            newMoto.setUser(client);
            newMoto.setBrand(dto.getBrand());
            newMoto.setModelName(dto.getModelName());
            newMoto.setYear(dto.getYear());
            newMoto.setLicensePlate(dto.getLicensePlate());
            return motorcycleService.save(newMoto);
        });

        orden.setUser(client);
        orden.setFullName(client.getFullName());
        orden.setIdentification(client.getIdentification());
        orden.setAppointmentDate(dto.getAppointmentDate());
        orden.setServiceType(serviceType);
        orden.setMotorcycle(motorcycle);

        repair.save(orden);
    }

    @Override
    public void deleteById(Long id) {
        repair.deleteById(id);
    }

    @Override
    public List<RepairOrder> findByStatus(OrderStatus status) {
        return repair.findByOrderStatus(status);
    }

    @Override
    public void createFromAdmin(AdminDateDTO dto, ServiceType serviceType) {
        RepairOrder order = new RepairOrder();

        Optional<User> userOpt = userService.findByIdentification(dto.getIdentification());

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        User user = userOpt.get();

        Optional<Motorcycle> existingMoto = motorcycleService.findByLicensePlateAndUser(dto.getLicensePlate(), user);
        if (existingMoto.isPresent()) {
            throw new IllegalArgumentException("Ya existe una motocicleta registrada con esa placa para este usuario.");
        }

        Motorcycle newMoto = new Motorcycle();
        newMoto.setUser(user);
        newMoto.setBrand(dto.getBrand());
        newMoto.setModelName(dto.getModelName());
        newMoto.setYear(dto.getYear());
        newMoto.setLicensePlate(dto.getLicensePlate());

        Motorcycle savedMoto = motorcycleService.save(newMoto);

        order.setUser(user);
        order.setFullName(dto.getFullName());
        order.setIdentification(dto.getIdentification());
        order.setAppointmentDate(dto.getAppointmentDate());
        order.setServiceType(serviceType);
        order.setOrderStatus(OrderStatus.NUEVO);
        order.setPriority(OrderPriority.BAJA);
        order.setMotorcycle(savedMoto);

        repair.save(order);
    }

    @Override
    public void updateFromAdmin(RepairOrder updatedOrder) {
        RepairOrder existingOrder = repair.findById(updatedOrder.getId())
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        Motorcycle updatedMoto = updatedOrder.getMotorcycle();
        Motorcycle existingMoto = existingOrder.getMotorcycle();

        if (!existingMoto.getLicensePlate().equalsIgnoreCase(updatedMoto.getLicensePlate())) {
            boolean placaDuplicada = motorcycleService.findByLicensePlateAndUser(
                    updatedMoto.getLicensePlate(), existingOrder.getUser()
            ).isPresent();

            if (placaDuplicada) {
                throw new IllegalArgumentException("Ya existe una motocicleta con esa placa para este usuario.");
            }
        }

        existingMoto.setBrand(updatedMoto.getBrand());
        existingMoto.setModelName(updatedMoto.getModelName());
        existingMoto.setYear(updatedMoto.getYear());
        existingMoto.setDisplacement(updatedMoto.getDisplacement());
        existingMoto.setKilometraje(updatedMoto.getKilometraje());
        existingMoto.setLicensePlate(updatedMoto.getLicensePlate());
        existingMoto.setColor(updatedMoto.getColor());

        motorcycleService.save(existingMoto);

        boolean mechanicAssigned = existingOrder.getMechanic() == null && updatedOrder.getMechanic() != null;
        boolean statusChanged = !existingOrder.getOrderStatus().equals(updatedOrder.getOrderStatus());

        OrderStatus oldStatus = existingOrder.getOrderStatus();

        existingOrder.setFullName(updatedOrder.getFullName());
        existingOrder.setIdentification(updatedOrder.getIdentification());
        existingOrder.setAppointmentDate(updatedOrder.getAppointmentDate());
        existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
        existingOrder.setServiceType(updatedOrder.getServiceType());
        existingOrder.setMechanic(updatedOrder.getMechanic());
        existingOrder.setPriority(updatedOrder.getPriority());
        existingOrder.setProblemDescription(updatedOrder.getProblemDescription());

        repair.save(existingOrder);

        if (mechanicAssigned) {
            notificationService.notifyUser(
                    existingOrder.getUser(),
                    "La orden de reparación de su moto ha sido asignada a un mecánico."
            );
        }

        if (statusChanged) {
            if (updatedOrder.getOrderStatus() == OrderStatus.COMPLETADO) {
                notificationService.notifyUser(
                        existingOrder.getUser(),
                        "La orden de reparación de su moto ha sido marcada como Completada."
                );
                notificationService.notifyUser(
                        existingOrder.getUser(),
                        "¡Gracias por confiar en nosotros! Por favor califique el servicio recibido.",
                        "FEEDBACK",
                        existingOrder.getId()
                );

            } else {
                notificationService.notifyUser(
                        existingOrder.getUser(),
                        String.format("La orden de reparación de su moto ha cambiado de estado de %s a %s.",
                                formatStatus(oldStatus), formatStatus(updatedOrder.getOrderStatus()))
                );
            }
        }
    }

    private String formatStatus(OrderStatus status) {
        return switch (status) {
            case NUEVO ->
                "Nuevo";
            case EN_PROGRESO ->
                "En Progreso";
            case EN_ESPERA ->
                "En Espera";
            case COMPLETADO ->
                "Completado";
            default ->
                status.name();
        };
    }

    @Override
    public List<RepairOrder> findByStatusASC(OrderStatus status) {
        return repair.findByOrderStatusOrderByAppointmentDateAsc(status);
    }

    @Override
    public List<RepairOrder> findByMechanicAndOrderStatusOrderByAppointmentDateAsc(Employee mechanic, OrderStatus status) {
        return repair.findByMechanicAndOrderStatusOrderByAppointmentDateAsc(mechanic, status);
    }

    @Override
    public void updateFromAdminOrMechanic(RepairOrder updatedOrder, Employee currentEmployee) {
        RepairOrder existingOrder = repair.findById(updatedOrder.getId())
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        boolean isAdmin = currentEmployee.getRoles().stream()
                .anyMatch(role -> role.equalsIgnoreCase("ADMIN"));

        boolean isMechanic = currentEmployee.getRoles().stream()
                .anyMatch(role -> role.equalsIgnoreCase("MECANICO"));

        Motorcycle updatedMoto = updatedOrder.getMotorcycle();
        Motorcycle existingMoto = existingOrder.getMotorcycle();

        OrderStatus previousStatus = existingOrder.getOrderStatus();

        if (isAdmin) {
            if (!existingMoto.getLicensePlate().equalsIgnoreCase(updatedMoto.getLicensePlate())) {
                boolean placaDuplicada = motorcycleService.findByLicensePlateAndUser(
                        updatedMoto.getLicensePlate(), existingOrder.getUser()
                ).isPresent();

                if (placaDuplicada) {
                    throw new IllegalArgumentException("Ya existe una motocicleta con esa placa para este usuario.");
                }
            }

            existingMoto.setBrand(updatedMoto.getBrand());
            existingMoto.setModelName(updatedMoto.getModelName());
            existingMoto.setYear(updatedMoto.getYear());
            existingMoto.setDisplacement(updatedMoto.getDisplacement());
            existingMoto.setKilometraje(updatedMoto.getKilometraje());
            existingMoto.setLicensePlate(updatedMoto.getLicensePlate());
            existingMoto.setColor(updatedMoto.getColor());
            motorcycleService.save(existingMoto);

            existingOrder.setAppointmentDate(updatedOrder.getAppointmentDate());
            existingOrder.setFullName(updatedOrder.getFullName());
            existingOrder.setIdentification(updatedOrder.getIdentification());
            existingOrder.setServiceType(updatedOrder.getServiceType());
            existingOrder.setPriority(updatedOrder.getPriority());
            existingOrder.setMechanic(updatedOrder.getMechanic());
            existingOrder.setProblemDescription(updatedOrder.getProblemDescription());
        } else if (isMechanic) {
            existingOrder.setServiceType(updatedOrder.getServiceType());
            existingOrder.setPriority(updatedOrder.getPriority());
            existingOrder.setProblemDescription(updatedOrder.getProblemDescription());

            existingMoto.setKilometraje(updatedMoto.getKilometraje());
            motorcycleService.save(existingMoto);
        }

        existingOrder.getUsedProducts().clear();

        if (updatedOrder.getUsedProducts() != null) {
            List<RepairOrderProduct> productosValidos = updatedOrder.getUsedProducts().stream()
                    .filter(p -> p.getProduct() != null && p.getQuantityUsed() != null && p.getQuantityUsed() > 0)
                    .collect(Collectors.toList());

            for (RepairOrderProduct usage : productosValidos) {
                usage.setRepairOrder(existingOrder);
                existingOrder.getUsedProducts().add(usage);
            }
        }

        OrderStatus newStatus = updatedOrder.getOrderStatus();
        existingOrder.setOrderStatus(newStatus);

        if (newStatus == OrderStatus.COMPLETADO && previousStatus != OrderStatus.COMPLETADO) {
            try {
                inventoryService.processProductUsage(existingOrder.getUsedProducts());
            } catch (InventoryException e) {
                throw new IllegalStateException("No se pudo finalizar la orden debido a problemas de inventario: " + e.getMessage());
            }
        }

        repair.save(existingOrder);

        if (!previousStatus.equals(newStatus)) {
            if (newStatus == OrderStatus.COMPLETADO) {
                notificationService.notifyUser(
                        existingOrder.getUser(),
                        "La orden de reparación de su moto ha sido marcada como Completada."
                );
                notificationService.notifyUser(
                        existingOrder.getUser(),
                        "¡Gracias por confiar en nosotros! Por favor califique el servicio recibido.",
                        "FEEDBACK",
                        existingOrder.getId()
                );
            } else {
                notificationService.notifyUser(
                        existingOrder.getUser(),
                        String.format("La orden de reparación de su moto ha cambiado de estado de %s a %s.",
                                formatStatus(previousStatus), formatStatus(newStatus))
                );
            }
        }
    }

    @Override
    public List<RepairOrder> findAll() {
        return repair.findAll();
    }

    @Override
    public List<RepairOrder> findByMotorcycle(Motorcycle motorcycle) {
        return repair.findByMotorcycle(motorcycle);
    }

}
