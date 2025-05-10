package com.api.services.serviceA;

import com.api.services.serviceA.dto.ExampleAInput;
import com.api.services.serviceA.dto.ExampleAOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExampleServiceA {
    Page<ExampleAOutput> getExamplePage(ExampleAInput input, Pageable pageable);

    ExampleAOutput testKafka();
}
