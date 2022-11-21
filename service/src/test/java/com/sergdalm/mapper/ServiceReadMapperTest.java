package com.sergdalm.mapper;

import com.sergdalm.EntityUtil;
import com.sergdalm.entity.Service;
import com.sergdalm.dto.ServiceReadDto;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor
class ServiceReadMapperTest {

    private final ServiceReadMapper serviceReadMapper;

    @Test
    void mapToDto() {
        Service serviceEntity = EntityUtil.getServiceClassicMassage();
        ServiceReadDto serviceReadDto = ServiceReadDto.builder()
                .id(serviceEntity.getId())
                .name(serviceEntity.getName())
                .description(serviceEntity.getDescription())
                .build();

        ServiceReadDto actualResult = serviceReadMapper.mapToDto(serviceEntity);

        assertEquals(serviceReadDto, actualResult);
        assertEquals(serviceReadDto.getName(), actualResult.getName());
        assertEquals(serviceReadDto.getDescription(), actualResult.getDescription());
    }

}