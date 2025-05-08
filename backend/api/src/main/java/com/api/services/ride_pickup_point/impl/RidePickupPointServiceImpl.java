package com.api.services.ride_pickup_point.impl;

import com.api.services.ride_pickup_point.RidePickupPointService;
import com.api.services.ride_pickup_point.dto.AddRidePickupPointInput;
import com.api.services.ride_pickup_point.dto.GetListRidePickupPointOutput;
import com.api.services.ride_pickup_point.dto.RidePickupPointFilterParam;
import com.api.services.ride_pickup_point.dto.UpdateRidePickupPointEmployeeInput;
import com.api.services.ride_pickup_point.dto.UpdateRidePickupPointInput;
import com.shared.db.dto.GetListRidePickupPointDTO;
import com.shared.db.entities.Account;
import com.shared.db.entities.Bus;
import com.shared.db.entities.Employee;
import com.shared.db.entities.Ride;
import com.shared.db.entities.RidePickupPoint;
import com.shared.db.entities.RidePickupPointHistory;
import com.shared.db.repo.EmployeeRepository;
import com.shared.db.repo.PickupPointRepository;
import com.shared.db.repo.RidePickupPointHistoryRepository;
import com.shared.db.repo.RidePickupPointRepository;
import com.shared.db.repo.RideRepository;
import com.shared.enumeration.BusStatus;
import com.shared.enumeration.RidePickupPointStatus;
import com.shared.enumeration.RideStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RidePickupPointServiceImpl implements RidePickupPointService {
    private final RidePickupPointRepository ridePickupPointRepository;
    private final RideRepository rideRepository;
    private final PickupPointRepository pickupPointRepository;
    private final RidePickupPointHistoryRepository ridePickupPointHistoryRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Page<GetListRidePickupPointOutput> getListRidePickupPoint(
            RidePickupPointFilterParam filterParam, Pageable pageable) {
        Page<GetListRidePickupPointDTO> pageData = ridePickupPointRepository.getListRidePickupPoint(
                filterParam.getPickupPointId(),
                filterParam.getRideId(),
                filterParam.getStatus(),
                pageable
        );
        return pageData.map(GetListRidePickupPointOutput::fromDto);
    }

    @Override
    @Transactional
    public void addRidePickupPoint(AddRidePickupPointInput addRidePickupPointInput) {
        // validate input
        for (AddRidePickupPointInput.ItemInput item : addRidePickupPointInput.getItems()) {
            // validate ride
            rideRepository.findById(item.getRideId())
                    .orElseThrow(() -> new IllegalArgumentException("Ride not found"));
            // validate pickup point
            pickupPointRepository.findById(item.getPickupPointId())
                    .orElseThrow(() -> new IllegalArgumentException("Pickup point not found"));
            // validate if ride pickup point already exists
            if (ridePickupPointRepository.existsByRideIdAndPickupPointId(item.getRideId(),
                    item.getPickupPointId())) {
                throw new IllegalArgumentException("Ride pickup point already exists");
            }
        }

        // save ride pickup point
        for (AddRidePickupPointInput.ItemInput item : addRidePickupPointInput.getItems()) {
            RidePickupPoint ridePickupPoint = ridePickupPointRepository.save(
                    RidePickupPoint.builder()
                            .ride(rideRepository.getReferenceById(item.getRideId()))
                            .pickupPoint(
                                    pickupPointRepository.getReferenceById(item.getPickupPointId()))
                            .status(RidePickupPointStatus.PICKING)
                            .build()
            );

            ridePickupPointHistoryRepository.save(
                    RidePickupPointHistory.builder()
                            .ridePickupPointId(ridePickupPoint.getPickupPoint().getId())
                            .pickupPointId(ridePickupPoint.getPickupPoint().getId())
                            .rideId(ridePickupPoint.getRide().getId())
                            .status(RidePickupPointStatus.PICKING)
                            .address(ridePickupPoint.getPickupPoint().getAddress())
                            .longitude(ridePickupPoint.getPickupPoint().getLongitude())
                            .latitude(ridePickupPoint.getPickupPoint().getLatitude())
                            .build()
            );
        }
    }

    @Override
    @Transactional
    public void updateRidePickupPoint(UpdateRidePickupPointInput updateRidePickupPointInput) {
        // validate input
        RidePickupPoint ridePickupPoint = ridePickupPointRepository.findById(
                        updateRidePickupPointInput.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ride pickup point not found"));

        // update ride pickup point
        ridePickupPoint.setStatus(updateRidePickupPointInput.getStatus());
        ridePickupPointRepository.save(ridePickupPoint);

        ridePickupPointHistoryRepository.save(
                RidePickupPointHistory.builder()
                        .ridePickupPointId(ridePickupPoint.getPickupPoint().getId())
                        .pickupPointId(ridePickupPoint.getPickupPoint().getId())
                        .rideId(ridePickupPoint.getRide().getId())
                        .status(updateRidePickupPointInput.getStatus())
                        .address(ridePickupPoint.getPickupPoint().getAddress())
                        .longitude(ridePickupPoint.getPickupPoint().getLongitude())
                        .latitude(ridePickupPoint.getPickupPoint().getLatitude())
                        .build()
        );
    }

    @Override
    @Transactional
    public void deleteRidePickupPoints(List<Long> ridePickupPointIds) {
        // validate input
        for (Long ridePickupPointId : ridePickupPointIds) {
            ridePickupPointRepository.findById(ridePickupPointId)
                    .orElseThrow(() -> new IllegalArgumentException("Ride pickup point not found"));
        }

        // delete ride pickup point
        ridePickupPointRepository.deleteAllById(ridePickupPointIds);
    }

    @Override
    @Transactional
    public void updateRidePickupPointEmployee(
            UpdateRidePickupPointEmployeeInput input,
            Account account) {
        Employee employee = employeeRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        RidePickupPoint ridePickupPoint = ridePickupPointRepository.findByRideIdAndPickupPointId(
                input.getRideId(),
                input.getPickupPointId()
        ).orElseThrow(() -> new IllegalArgumentException("Ride pickup point not found"));

        // validate if employee is assigned to ride pickup point
        Bus bus = ridePickupPoint.getRide().getBus();
        if (!bus.getDriverId().equals(employee.getId()) &&
                !bus.getDriverMateId().equals(employee.getId())) {
            throw new IllegalArgumentException("Employee is not assigned to ride pickup point");
        }

        Ride ride = ridePickupPoint.getRide();
        if (input.getStatus().equals(RidePickupPointStatus.PICKED) &&
                !bus.getStatus().equals(BusStatus.RUNNING) &&
                !ride.getStatus().equals(RideStatus.RUNNING)) {
            throw new IllegalArgumentException("Bus or ride is not running");
        }

        ridePickupPoint.setStatus(input.getStatus());
        ridePickupPointRepository.save(ridePickupPoint);
        if (!input.getStatus().equals(RidePickupPointStatus.PICKING)) {
            ridePickupPointHistoryRepository.save(
                    RidePickupPointHistory.fromEntity(ridePickupPoint)
            );
        }
    }
}
