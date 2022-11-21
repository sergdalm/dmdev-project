package com.sergdalm.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Value
@Builder
public class AddressCreateDto {

    @NotEmpty
    @NotBlank
    String addressName;

    @NotEmpty
    @NotBlank
    String description;
}
