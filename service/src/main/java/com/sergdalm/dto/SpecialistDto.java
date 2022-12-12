package com.sergdalm.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SpecialistDto {
    Integer id;
    String fullName;
    String image;
    String description;
}
