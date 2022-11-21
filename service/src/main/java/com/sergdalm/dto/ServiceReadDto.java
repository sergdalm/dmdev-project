package com.sergdalm.dto;

import com.sergdalm.entity.ServiceName;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ServiceReadDto {
    Integer id;
    ServiceName name;
    String description;
}
