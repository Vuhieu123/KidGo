package com.api.services.parent_student.impl;

import com.api.services.common_dto.BusOutput;
import com.api.services.common_dto.PickupPointOutput;
import com.api.services.common_dto.RideOutput;
import com.api.services.common_dto.RidePickupPointOutput;
import com.api.services.common_dto.StudentOutput;
import com.api.services.parent_student.ParentStudentService;
import com.api.services.parent_student.dto.GetStudentRideOutput;
import com.shared.db.entities.Account;
import com.shared.db.entities.Parent;
import com.shared.db.entities.PickupPoint;
import com.shared.db.entities.Ride;
import com.shared.db.entities.Student;
import com.shared.db.entities.StudentAssign;
import com.shared.db.repo.BusRepository;
import com.shared.db.repo.ParentRepository;
import com.shared.db.repo.PickupPointRepository;
import com.shared.db.repo.RidePickupPointRepository;
import com.shared.db.repo.RideRepository;
import com.shared.db.repo.StudentAssignRepository;
import com.shared.db.repo.StudentPickupPointRepository;
import com.shared.db.repo.StudentRepository;
import com.shared.exception.MyException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParentStudentServiceImpl implements ParentStudentService {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final StudentPickupPointRepository studentPickupPointRepository;
    private final PickupPointRepository pickupPointRepository;
    private final RidePickupPointRepository ridePickupPointRepository;
    private final RideRepository rideRepository;
    private final BusRepository busRepository;
    private final StudentAssignRepository studentAssignRepository;

    @Override
    public List<GetStudentRideOutput> getStudentRides(Account account) {
        Parent parent = parentRepository.findByAccountId(account.getId()).orElseThrow(
                () -> new MyException(
                        null,
                        "parent_not_found",
                        "Parent not found for account id: " + account.getId(),
                        HttpStatus.BAD_REQUEST
                )
        );

        List<Long> studentIds = studentRepository.findStudentIdsByParentId(parent.getId());

        return getStudentRides(studentIds);
    }

    @Override
    public List<GetStudentRideOutput> getStudentRides(List<Long> studentIds) {
        List<GetStudentRideOutput> result = new ArrayList<>();

        for (Long studentId : studentIds) {
            Student student = studentRepository.findById(studentId).orElseThrow(
                    () -> new MyException(
                            null,
                            "student_not_found",
                            "Student not found for id: " + studentId,
                            HttpStatus.BAD_REQUEST
                    )
            );
            PickupPoint pickupPoint = pickupPointRepository.findByStudentId(studentId)
                    .orElse(null);


            StudentOutput studentOutput = StudentOutput.fromEntity(student);
            PickupPointOutput pickupPointOutput = pickupPoint == null ? null :
                    PickupPointOutput.fromEntity(pickupPoint);
            List<GetStudentRideOutput.ExecutionOutput> executionOutputs = new ArrayList<>();

            if (pickupPoint != null) {
                List<Ride> rides = rideRepository.findByPickupPointId(pickupPoint.getId());

                for (Ride ride: rides) {
                    GetStudentRideOutput.ExecutionOutput executionOutput = new GetStudentRideOutput.ExecutionOutput();
                    executionOutput.setRide(RideOutput.fromEntity(ride));
                    executionOutput.setBus(BusOutput.fromEntity(ride.getBus()));
                    executionOutput.setPickupPoints(
                            ridePickupPointRepository.findPickupPointsByRideId( ride.getId() ).stream()
                                    .map(PickupPointOutput::fromEntity)
                                    .toList()
                    );
                    executionOutput.setRidePickupPoints(
                            ridePickupPointRepository.findByRideId(ride.getId()).stream()
                                    .map(RidePickupPointOutput::fromEntity)
                                    .toList()
                    );
                    executionOutputs.add(executionOutput);
                }
            }

            // enrich student assign - bus with number plate
            StudentAssign studentAssign = studentAssignRepository.findByStudentId(studentId).orElse(null);
            if (studentAssign != null) {
                studentOutput.setNumberPlateAssign(studentAssign.getNumberPlate());
            }

            GetStudentRideOutput getStudentRideOutput = GetStudentRideOutput.builder()
                    .student(studentOutput)
                    .pickupPoint(pickupPointOutput)
                    .executions(executionOutputs)
                    .build();
            result.add(getStudentRideOutput);
        }

        return result;
    }
}
