package com.sergdalm.mapper;

import com.sergdalm.EntityUtil;
import com.sergdalm.dto.AddressReadDto;
import com.sergdalm.entity.Address;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor
class AddressReadMapperTest {

    private final AddressReadMapper addressReadMapper;
    private final AddressReadDto addressReadDto = AddressReadDto.builder()
            .id(EntityUtil.getAddressNarvskaya().getId())
            .addressName(EntityUtil.getAddressNarvskaya().getAddressName())
            .description(EntityUtil.getAddressNarvskaya().getDescription())
            .build();

    @Test
    void mapToDto() {
        Address address = EntityUtil.getAddressNarvskaya();
        AddressReadDto actualResult = addressReadMapper.mapToDto(address);
        assertEquals(addressReadDto, actualResult);
    }

}