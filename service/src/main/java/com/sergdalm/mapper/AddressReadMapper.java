package com.sergdalm.mapper;

import com.sergdalm.dto.AddressReadDto;
import com.sergdalm.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressReadMapper implements ReadMapper<Address, AddressReadDto> {

    @Override
    public AddressReadDto mapToDto(Address entity) {
        return AddressReadDto.builder()
                .id(entity.getId())
                .addressName(entity.getAddressName())
                .description(entity.getDescription())
                .build();
    }
}
