package com.api.controllers.admin.dto.bus;

import com.api.services.bus.dto.AddBusInput;
import com.shared.enumeration.BusStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@Builder
public class AddBusRequest {

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{2}[A-Z]{1,2}-[0-9]{3}\\.[0-9]{2}$",
            message = "Biển số xe không đúng định dạng Việt Nam"
    )
    private String numberPlate;

    @NotNull
    @Min(value = 1, message = "Số ghế phải lớn hơn hoặc bằng 1")
    @Max(value = 50, message = "Số ghế không được vượt quá 50")
    private Integer seatNumber;

    @NotNull
    private Long driverId;

    @NotNull
    private Long driverMateId;

    @NotNull
    private BusStatus status;

    public AddBusInput toInput() {
        return AddBusInput.builder()
                .numberPlate(numberPlate)
                .seatNumber(seatNumber)
                .driverId(driverId)
                .driverMateId(driverMateId)
                .status(status)
                .build();
    }

}
