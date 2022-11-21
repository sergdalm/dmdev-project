package com.sergdalm.mapper;

import com.sergdalm.dto.AddressCreateDto;
import com.sergdalm.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressCreateMapper implements CreateEditMapper<Address, AddressCreateDto> {

    @Override
    public Address mapToEntity(AddressCreateDto dto) {
        return Address.builder()
                .addressName(dto.getAddressName())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public Address mapToEntity(AddressCreateDto dto, Address entity) {
        entity.setAddressName(dto.getAddressName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
