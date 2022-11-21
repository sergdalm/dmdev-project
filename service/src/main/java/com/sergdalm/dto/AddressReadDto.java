package com.sergdalm.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddressReadDto {

    Integer id;
    String addressName;
    String description;
}
