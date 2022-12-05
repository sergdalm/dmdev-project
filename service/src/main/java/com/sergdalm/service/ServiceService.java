package com.sergdalm.service;

import com.sergdalm.dao.ServiceRepository;
import com.sergdalm.dto.ServiceCreateDto;
import com.sergdalm.dto.ServiceReadDto;
import com.sergdalm.mapper.ServiceCreateMapper;
import com.sergdalm.mapper.ServiceReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ServiceService implements CrudServiceWithSingleReadDto<Integer, ServiceCreateDto, ServiceReadDto> {

    private final ServiceRepository serviceRepository;
    private final ServiceCreateMapper serviceCreateMapper;
    private final ServiceReadMapper serviceReadMapper;

    @Override
    public List<ServiceReadDto> findAll() {
        return serviceRepository.findAll().stream()
                .map(serviceReadMapper::mapToDto)
                .toList();
    }

    @Override
    public Optional<ServiceReadDto> findById(Integer id) {
        return serviceRepository.findById(id)
                .map(serviceReadMapper::mapToDto);
    }

    @Override
    public ServiceReadDto create(ServiceCreateDto dto) {
        return Optional.of(dto)
                .map(serviceCreateMapper::mapToEntity)
                .map(serviceRepository::save)
                .map(serviceReadMapper::mapToDto)
                .orElseThrow();
    }

    @Override
    public Optional<ServiceReadDto> update(Integer id, ServiceCreateDto dto) {
        return serviceRepository.findById(id)
                .map(serviceRepository::save)
                .map(serviceReadMapper::mapToDto);
    }

    @Override
    public boolean delete(Integer id) {
        return serviceRepository.findById(id)
                .map(entity -> {
                    serviceRepository.delete(entity);
                    serviceRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
