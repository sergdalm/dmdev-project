package com.sergdalm.service;

import com.sergdalm.dao.UserInfoRepository;
import com.sergdalm.dao.UserRepository;
import com.sergdalm.dao.filter.SpecialistFilter;
import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.dto.UserReadDto;
import com.sergdalm.dto.UserWithInfoDto;
import com.sergdalm.entity.User;
import com.sergdalm.entity.UserInfo;
import com.sergdalm.mapper.UserCreateEditMapper;
import com.sergdalm.mapper.UserCreateEditToUserInfoMapper;
import com.sergdalm.mapper.UserReadMapper;
import com.sergdalm.mapper.UserWithInfoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService implements CrudServiceWithDoubleReadDto<Integer, UserCreateEditDto, UserReadDto, UserWithInfoDto> {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final ImageService imageService;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserWithInfoMapper userWithInfoMapper;
    private final UserCreateEditToUserInfoMapper userCreateEditToUserInfoMapper;

    @Override
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::mapToDto)
                .toList();
    }

    public List<UserWithInfoDto> findAll(SpecialistFilter filter) {
        return userRepository.findSpecialistsByFilter(filter).stream()
                .map(userWithInfoMapper::mapToDto)
                .toList();
    }

    @Override
    public Optional<UserWithInfoDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userWithInfoMapper::mapToDto);
    }

    @Transactional
    @Override
    public UserWithInfoDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(newUserDto -> {
                    User user = userCreateEditMapper.mapToEntity(newUserDto);
                    UserInfo userInfo = userCreateEditToUserInfoMapper.mapToEntity(newUserDto);
                    userRepository.save(user);
                    userInfo.setUser(user);
                    userInfoRepository.save(userInfo);
                    return userWithInfoMapper.mapToDto(user);
                })
                .orElseThrow();
    }

    @Transactional
    @Override
    public Optional<UserWithInfoDto> update(Integer id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.mapToEntity(userDto, entity))
                .map(entity -> {
                    userRepository.saveAndFlush(entity);
                    userInfoRepository.findById(id)
                            .map(userInfo -> userCreateEditToUserInfoMapper.mapToEntity(userDto, userInfo))
                            .map(userInfoRepository::saveAndFlush);
                    return entity;
                })
                .map(userWithInfoMapper::mapToDto);
    }

    @Transactional
    @Override
    public boolean delete(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Optional<byte[]> findAvatar(Integer id) {
        return userInfoRepository.findById(id)
                .map(UserInfo::getImage)
                .filter(org.springframework.util.StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
