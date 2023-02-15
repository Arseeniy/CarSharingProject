package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.common.dto.*;
import com.arseeniy.mycarsharing.common.entity.authorization.UsersData;
import com.arseeniy.mycarsharing.common.entity.booking.Fare;
import com.arseeniy.mycarsharing.common.entity.booking.Order;
import com.arseeniy.mycarsharing.common.entity.booking.OrderStatus;
import com.arseeniy.mycarsharing.common.entity.booking.Vehicle;
import com.arseeniy.mycarsharing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FareRepository fareRepository;

    @Autowired
    private UsersDataRepository usersDataRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<AvailableVehicleViewer> getAvailableVehicleList(List<Vehicle> vehicleList) {

        List<AvailableVehicleViewer> availableVehicleViewerList = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.isAvailable()) {
                availableVehicleViewerList.add(new AvailableVehicleViewer(vehicle.getStateNumber(),
                        vehicle.getVehicleModel(),
                        vehicle.getVehicleClass(),
                        vehicle.getTransmissionType(),
                        fareRepository.findByVehicleModel(vehicle.getVehicleModel()).getDayPrice()));
            }
        }
        return availableVehicleViewerList;
    }

    @Override
    public List<AvailableVehicleViewer> getAvailableVehicleListByDate(List<Vehicle> vehicles, Date startDate, Date endDate) {
        List<AvailableVehicleViewer> availableVehicleViewerArrayList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Date dateEnd = vehicle.getRentingEnd();
            if (vehicle.isAvailable() || vehicle.getRentingEnd().before(startDate)) {
                availableVehicleViewerArrayList.add(new AvailableVehicleViewer(vehicle.getStateNumber(),
                        vehicle.getVehicleModel(),
                        vehicle.getVehicleClass(),
                        vehicle.getTransmissionType(),
                        fareRepository.findByVehicleModel(vehicle.getVehicleModel()).getDayPrice()));
            }
        }
        return availableVehicleViewerArrayList;
    }

    @Override
    public BookingVehicleViewer chooseForBooking(String stateNumber) {

        Vehicle vehicle = vehicleRepository.findByStateNumber(stateNumber);

        Fare farePerDay = fareRepository.findByVehicleModel(vehicle.getVehicleModel());


        BookingVehicleViewer bookingVehicleViewer = new BookingVehicleViewer(stateNumber,
                vehicle.getVehicleModel(),
                vehicle.getVehicleClass(),
                vehicle.getDoorsCount(),
                vehicle.getPassengersCount(),
                vehicle.isAirConditioning(),
                vehicle.isAudio(),
                vehicle.getFuelType(),
                vehicle.getFuelTankCapacity(),
                vehicle.getEnginePower(),
                vehicle.getTransmissionType(),
                farePerDay.getDayPrice());

        return bookingVehicleViewer;
    }

    @Override
    public ResponseEntity<String> createOrder(OrderCreationRequest orderCreationRequest) {

        if (!vehicleRepository.existsByStateNumber(orderCreationRequest.getStateNumber())) {
            return ResponseEntity.badRequest().body("Такого автомобиля не существует.");
        }
        if (!orderCreationRequest.getPassportNumber().matches("(\\d{4})[ ](\\d{6})")) {
            return ResponseEntity.badRequest().body("Некорректные номер/серия паспорта.");
        }
        if (orderCreationRequest.getIssuingAuthority().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Некорректные сведения о государственном органе.");
        }
        if (orderCreationRequest.getRegistrationPlace().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Некорректные сведения о месте прописки.");
        }
        if (!orderCreationRequest.getDriverLicenseNumber().matches("(\\d{2})[ ](\\d{2})[ ](\\d{6})")) {
            return ResponseEntity.badRequest().body("Некорректные номер/серия водительского удостоверения.");

        }
        vehicleRepository.bookVehicle(orderCreationRequest.getStateNumber(),
                orderCreationRequest.getRentingStart(), orderCreationRequest.getRentingEnd());

        userRepository.bookVehicle(
                orderCreationRequest.getStateNumber(),
                SecurityContextHolder.getContext().getAuthentication().getName());

        usersDataRepository.save(new UsersData(orderCreationRequest.getFirstName(),
                orderCreationRequest.getLastName(),
                orderCreationRequest.getBirthDate(),
                orderCreationRequest.getPassportNumber(),
                orderCreationRequest.getPassportIssueDate(),
                orderCreationRequest.getIssuingAuthority(),
                orderCreationRequest.getRegistrationPlace(),
                orderCreationRequest.getDriverLicenseNumber(),
                orderCreationRequest.getDriverLicenseIssueDate()));

        Vehicle vehicle = vehicleRepository.findByStateNumber(orderCreationRequest.getStateNumber());

        Fare farePerDay = fareRepository.findByVehicleModel(vehicle.getVehicleModel());

        Double rentPrice = ChronoUnit.DAYS.between(orderCreationRequest.getRentingStart(), orderCreationRequest.getRentingEnd())
                * farePerDay.getDayPrice();

//        orderRepository.createOrder(SecurityContextHolder.getContext().getAuthentication().getName(),
//                orderCreationRequest.getStateNumber(),
//                vehicle.getVehicleModel(),
//                OrderStatus.AWAITING_CONFIRMATION,
//                farePerDay.getDayPrice(),
//                rentPrice,
//                orderCreationRequest.getRentingStart(),
//                orderCreationRequest.getRentingEnd());

        orderRepository.saveAndFlush(new Order(SecurityContextHolder.getContext().getAuthentication().getName(),
                orderCreationRequest.getStateNumber(),
                vehicle.getVehicleModel(),
                OrderStatus.AWAITING_CONFIRMATION,
                farePerDay.getDayPrice(),
                rentPrice,
                orderCreationRequest.getRentingStart(),
                orderCreationRequest.getRentingEnd()));

        return ResponseEntity.ok("Автомобиль забронирован!");
    }

    @Override
    public String closeVehicleRenting(CloseRentingRequest closeRentingRequest) {

        vehicleRepository.closeVehicleRenting(closeRentingRequest.getStateNumber());
        userRepository.closeRenting(SecurityContextHolder.getContext().getAuthentication().getName());
        orderRepository.closeOrder(closeRentingRequest.getStateNumber());

        return "Аренда успешно закрыта.";
    }

    @Override
    public String rejectVehicleRenting(OrderReject orderReject) {

        vehicleRepository.closeVehicleRenting(orderReject.getStateNumber());
        userRepository.closeRenting(SecurityContextHolder.getContext().getAuthentication().getName());
        orderRepository.rejectOrder(orderReject.getStateNumber(), orderReject.getRejectDescription());

        return "Заказ отклонен!";
    }

    @Override
    public String vehicleCreation(RequestForVehicleCreation requestForCreation) {

        vehicleRepository.save(new Vehicle(requestForCreation.getStateNumber(),
                requestForCreation.getVehicleModel(),
                requestForCreation.getVehicleClass(),
                requestForCreation.getDoorsCount(),
                requestForCreation.getDoorsCount(),
                requestForCreation.isAirConditioning(),
                requestForCreation.isAudio(),
                requestForCreation.getFuelType(),
                requestForCreation.getFuelTankCapacity(),
                requestForCreation.getEnginePower(),
                requestForCreation.getTransmissionType(),
                requestForCreation.isAvailable()));

        return "Автомобиль успешно создан.";
    }
}
