package com.shared.db.dto;

import com.shared.db.entities.Parent;
import com.shared.db.entities.RequestRegistration;
import com.shared.db.entities.Student;

public interface GetListRequestRegistrationDTO {
    Student getStudent();
    Parent getParent();
    RequestRegistration getRequestRegistration();
}
