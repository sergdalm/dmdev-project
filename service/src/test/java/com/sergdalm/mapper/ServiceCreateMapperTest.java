package com.sergdalm.mapper;

import com.sergdalm.EntityUtil;
import com.sergdalm.dto.ServiceCreateDto;
import com.sergdalm.entity.Service;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor
class ServiceCreateMapperTest {

    private final ServiceCreateMapper serviceCreateMapper;

    @Test
    void mapToEntityFromDto() {
        ServiceCreateDto serviceCreateDto = ServiceCreateDto.builder()
                .name(EntityUtil.getServiceClassicMassage().getName())
                .description(EntityUtil.getServiceClassicMassage().getDescription())
                .build();
        Service service = Service.builder()
                .name(EntityUtil.getServiceClassicMassage().getName())
                .description(EntityUtil.getServiceClassicMassage().getDescription())
                .build();

        Service actualResult = serviceCreateMapper.mapToEntity(serviceCreateDto);

        assertEquals(service, actualResult);
        assertEquals(service.getName(), actualResult.getName());
        assertEquals(service.getDescription(), actualResult.getDescription());
    }

    @Test
    void mapToEntityFromDtoAndEntity() {
        ServiceCreateDto serviceCreateDto = ServiceCreateDto.builder()
                .name(EntityUtil.getServiceClassicMassage().getName())
                .description("Good")
                .build();
        Service previousService = EntityUtil.getServiceClassicMassage();
        Service updatedService = Service.builder()
                .id(EntityUtil.getServiceClassicMassage().getId())
                .name(EntityUtil.getServiceClassicMassage().getName())
                .description("Good")
                .build();


        Service actualResult = serviceCreateMapper.mapToEntity(serviceCreateDto, previousService);

        assertEquals(updatedService, actualResult);
        assertEquals(updatedService.getName(), actualResult.getName());
        assertEquals(updatedService.getDescription(), actualResult.getDescription());
    }

}