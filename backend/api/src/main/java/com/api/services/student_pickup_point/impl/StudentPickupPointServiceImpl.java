package com.api.services.student_pickup_point.impl;

import com.api.services.student_pickup_point.StudentPickupPointService;
import com.api.services.student_pickup_point.dto.UpdateStudentPickupPointEmployeeInput;
import com.shared.db.entities.Account;
import com.shared.db.entities.Bus;
import com.shared.db.entities.Employee;
import com.shared.db.entities.PickupPoint;
import com.shared.db.entities.Ride;
import com.shared.db.entities.StudentPickupPoint;
import com.shared.db.entities.StudentPickupPointHistory;
import com.shared.db.repo.BusRepository;
import com.shared.db.repo.EmployeeRepository;
import com.shared.db.repo.PickupPointRepository;
import com.shared.db.repo.RideRepository;
import com.shared.db.repo.StudentPickupPointHistoryRepository;
import com.shared.db.repo.StudentPickupPointRepository;
import com.shared.db.repo.StudentRepository;
import com.shared.enumeration.StudentPickupPointStatus;
import com.shared.exception.MyException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentPickupPointServiceImpl implements StudentPickupPointService {
    private final StudentPickupPointRepository studentPickupPointRepository;
    private final StudentPickupPointHistoryRepository studentPickupPointHistoryRepository;
    private final EmployeeRepository employeeRepository;
    private final PickupPointRepository pickupPointRepository;
    private final StudentRepository studentRepository;
    private final RideRepository rideRepository;
    private final BusRepository busRepository;

    @Override
    @Transactional
    public void updateStudentPickupPointEmployee(UpdateStudentPickupPointEmployeeInput input,
                                                 Account account) {
        Employee employee = employeeRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // validate input
        List<StudentPickupPoint> studentPickupPoints = input.getStudentIds().stream()
                .map(studentId -> studentPickupPointRepository.findByStudentIdAndPickupPointId(
                                studentId, input.getPickupPointId())
                        .orElseThrow(() -> new IllegalArgumentException("Student pickup point not found"))
                ).toList();

        // validate status to ensure that it will be updated following the correct order
        for (StudentPickupPoint studentPickupPoint : studentPickupPoints) {
            switch (input.getStatus()) {
                case PICKING -> {
                    throw new MyException(
                            null,
                            "Invalid status",
                            "Cannot update status to PICKING",
                            HttpStatus.BAD_REQUEST
                    );
                }
                case PICKED, MISSED -> {
                    if (studentPickupPoint.getStatus() != StudentPickupPointStatus.PICKING &&
                            studentPickupPoint.getStatus() != StudentPickupPointStatus.AT_SCHOOL) {
                        throw new MyException(
                                null,
                                "Invalid status",
                                String.format("Student %d is not in picking status", studentPickupPoint.getStudent().getId()),
                                HttpStatus.BAD_REQUEST
                        );
                    }
                }
                case AT_SCHOOL, AT_HOME -> {
                    if (studentPickupPoint.getStatus() != StudentPickupPointStatus.PICKED) {
                        throw new MyException(
                                null,
                                "Invalid status",
                                String.format("Student %d is not in picked status", studentPickupPoint.getStudent().getId()),
                                HttpStatus.BAD_REQUEST
                        );
                    }
                }
            }
        }

        // validate ride belongs to employee
        Ride ride = rideRepository.findById(input.getRideId())
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));
        if (!ride.getBus().getDriverId().equals(employee.getId()) &&
                !ride.getBus().getDriverMateId().equals(employee.getId())) {
            throw new MyException(
                    null,
                    "Invalid ride",
                    "Ride does not belong to employee",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (ride.getIsToSchool() && input.getStatus() == StudentPickupPointStatus.AT_HOME) {
            throw new MyException(
                    null,
                    "Invalid status",
                    "Cannot update status to AT_HOME for ride to school",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (!ride.getIsToSchool() && input.getStatus() == StudentPickupPointStatus.AT_SCHOOL) {
            throw new MyException(
                    null,
                    "Invalid status",
                    "Cannot update status to AT_SCHOOL for ride to home",
                    HttpStatus.BAD_REQUEST
            );
        }

        PickupPoint pickupPoint = pickupPointRepository.findById(input.getPickupPointId())
                .orElseThrow(() -> new IllegalArgumentException("Pickup point not found"));

        // update student pickup point
        studentPickupPoints.forEach(studentPickupPoint -> {
            studentPickupPoint.setStatus(input.getStatus());
        });

        // save student pickup point history
        List<StudentPickupPointHistory> studentPickupPointHistories = studentPickupPoints.stream()
                .map(studentPickupPoint -> new StudentPickupPointHistory(
                        studentPickupPoint,
                        pickupPoint.getAddress(),
                        pickupPoint.getLatitude(),
                        pickupPoint.getLongitude(),
                        input.getRideId()
                )).toList();

        studentPickupPointRepository.saveAll(studentPickupPoints);
        studentPickupPointHistoryRepository.saveAll(studentPickupPointHistories);
    }
}
