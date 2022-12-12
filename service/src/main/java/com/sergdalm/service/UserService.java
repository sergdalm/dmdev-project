package com.sergdalm.service;

import com.sergdalm.dao.UserInfoRepository;
import com.sergdalm.dao.UserRepository;
import com.sergdalm.dao.filter.SpecialistFilter;
import com.sergdalm.dao.filter.UserFilter;
import com.sergdalm.dto.SpecialistDto;
import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.dto.UserDto;
import com.sergdalm.dto.UserReadDto;
import com.sergdalm.dto.UserWithInfoDto;
import com.sergdalm.entity.User;
import com.sergdalm.entity.UserInfo;
import com.sergdalm.mapper.SpecialistReadMapper;
import com.sergdalm.mapper.UserCreateEditMapper;
import com.sergdalm.mapper.UserCreateEditToUserInfoMapper;
import com.sergdalm.mapper.UserReadMapper;
import com.sergdalm.mapper.UserWithInfoMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService implements CrudServiceWithDoubleReadDto<Integer, UserCreateEditDto, UserReadDto, UserWithInfoDto>, UserDetailsService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final ImageService imageService;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserWithInfoMapper userWithInfoMapper;
    private final UserCreateEditToUserInfoMapper userCreateEditToUserInfoMapper;
    private final EntityManager entityManager;
    private final SpecialistReadMapper specialistReadMapper;

    @Override
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::mapToDto)
                .toList();
    }

    public List<UserWithInfoDto> findAll(UserFilter filter) {
        return userRepository.findAll(filter).stream()
                .map(userWithInfoMapper::mapToDto)
                .toList();
    }

    public Page<SpecialistDto> findAll(SpecialistFilter filter, Pageable pageable) {
        return userRepository.findAll(filter, pageable)
                .map(specialistReadMapper::mapToDto);
    }

    @Override
    public Optional<UserWithInfoDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userWithInfoMapper::mapToDto);
    }

    public Optional<SpecialistDto> findSpecialistById(Integer id) {
        return userRepository.findById(id)
                .map(specialistReadMapper::mapToDto);
    }

    @Transactional
    @Override
    public UserWithInfoDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(newUserDto -> {
                    User user = userCreateEditMapper.mapToEntity(newUserDto);
                    UserInfo userInfo = userCreateEditToUserInfoMapper.mapToEntity(newUserDto);
                    uploadImage(userDto.getImage());
                    userRepository.save(user);
                    userRepository.flush();
                    userInfo.setUser(user);
                    // При попытке сохранить через userInfoRepository падает исключение
                    userInfoRepository.save(userInfo);
//                    entityManager.persist(userInfo);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new UserDto(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole()),
                        user.getId(),
                        user.getFirstName()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user " + email));
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}
