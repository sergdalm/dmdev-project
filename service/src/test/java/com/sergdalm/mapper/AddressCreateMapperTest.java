package com.sergdalm.mapper;

import com.sergdalm.EntityUtil;
import com.sergdalm.dto.AddressCreateDto;
import com.sergdalm.entity.Address;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor
class AddressCreateMapperTest {

    private final AddressCreateMapper addressCreateMapper;
    private final AddressCreateDto addressCreateDto = AddressCreateDto.builder()
            .addressName(EntityUtil.getAddressNarvskaya().getAddressName())
            .description(EntityUtil.getAddressNarvskaya().getDescription())
            .build();

    @Test
    void mapToEntityFromDto() {
        Address address = Address.builder()
                .addressName(EntityUtil.getAddressNarvskaya().getAddressName())
                .description(EntityUtil.getAddressNarvskaya().getDescription())
                .build();

        Address actualResult = addressCreateMapper.mapToEntity(addressCreateDto);

        assertEquals(address, actualResult);
    }

    @Test
    void mapToEntityFromDtoAndEntity() {
        Address previousAddress = EntityUtil.getAddressNarvskaya();
        String newName = "Nevsky";
        String newDescription = "Nevsky pr. 17, 5";
        Address updatedAddress = Address.builder()
                .id(previousAddress.getId())
                .addressName(newName)
                .description(newDescription)
                .build();

        AddressCreateDto updatedAddressDto = AddressCreateDto.builder()
                .addressName(newName)
                .description(newDescription)
                .build();

        Address actualResult = addressCreateMapper.mapToEntity(updatedAddressDto, previousAddress);

        assertEquals(updatedAddress, actualResult);
        assertEquals(newDescription, actualResult.getDescription());
        assertEquals(newName, actualResult.getAddressName());
    }
}
