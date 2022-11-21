package com.sergdalm.dto;

import com.sergdalm.entity.ServiceName;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ServiceCreateDto {
    ServiceName name;
    String description;
}
