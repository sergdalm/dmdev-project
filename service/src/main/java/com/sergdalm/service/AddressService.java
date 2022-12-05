package com.sergdalm.service;

import com.sergdalm.dao.AddressRepository;
import com.sergdalm.dto.AddressCreateDto;
import com.sergdalm.dto.AddressReadDto;
import com.sergdalm.mapper.AddressCreateMapper;
import com.sergdalm.mapper.AddressReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AddressService implements CrudServiceWithSingleReadDto<Integer, AddressCreateDto, AddressReadDto> {

    private final AddressRepository addressRepository;
    private final AddressCreateMapper addressCreateMapper;
    private final AddressReadMapper addressReadMapper;

    @Override
    public List<AddressReadDto> findAll() {
        return addressRepository.findAll().stream()
                .map(addressReadMapper::mapToDto)
                .toList();
    }

    @Override
    public Optional<AddressReadDto> findById(Integer id) {
        return addressRepository.findById(id)
                .map(addressReadMapper::mapToDto);
    }

    @Transactional
    @Override
    public AddressReadDto create(AddressCreateDto addressDto) {
        return Optional.of(addressDto)
                .map(addressCreateMapper::mapToEntity)
                .map(addressRepository::save)
                .map(addressReadMapper::mapToDto)
                .orElseThrow();
    }

    @Override
    public Optional<AddressReadDto> update(Integer id, AddressCreateDto addressDto) {
        return addressRepository.findById(id)
                .map(entity -> addressCreateMapper.mapToEntity(addressDto, entity))
                .map(addressRepository::saveAndFlush)
                .map(addressReadMapper::mapToDto);
    }

    @Transactional
    @Override
    public boolean delete(Integer id) {
        return addressRepository.findById(id)
                .map(entity -> {
                    addressRepository.delete(entity);
                    addressRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
