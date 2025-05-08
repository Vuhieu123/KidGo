package com.api.services.request_registration.dto;

import com.api.services.common_dto.ParentOutput;
import com.api.services.common_dto.RequestRegistrationOutput;
import com.api.services.common_dto.StudentOutput;
import com.shared.db.dto.GetListRequestRegistrationDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetListRequestRegistrationOutput {
    private ParentOutput parent;
    private StudentOutput student;
    private RequestRegistrationOutput requestRegistration;

    public static GetListRequestRegistrationOutput fromDTO(GetListRequestRegistrationDTO dto) {
        return GetListRequestRegistrationOutput.builder()
                .parent(ParentOutput.fromEntity(dto.getParent()))
                .student(StudentOutput.fromEntity(dto.getStudent()))
                .requestRegistration(RequestRegistrationOutput.fromEntity(dto.getRequestRegistration()))
                .build();
    }
}