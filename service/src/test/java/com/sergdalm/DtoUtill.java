package com.sergdalm;

import com.sergdalm.dto.UserCreateEditDto;
import com.sergdalm.entity.Gender;
import com.sergdalm.entity.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoUtill {

    public final static UserCreateEditDto NEW_USER_SPECIALIST_DTO = UserCreateEditDto.builder()
            .email(EntityUtil.getSpecialistDmitry().getEmail())
            .mobilePhoneNumber(EntityUtil.getSpecialistDmitry().getMobilePhoneNumber())
            .role(EntityUtil.getSpecialistDmitry().getRole())
            .password(EntityUtil.getSpecialistDmitry().getPassword())
            .gender(EntityUtil.getSpecialistDmitry().getUserInfo().getGender())
            .birthday(EntityUtil.getSpecialistDmitry().getUserInfo().getBirthday())
            .firstName(EntityUtil.getSpecialistDmitry().getFirstName())
            .lastName(EntityUtil.getSpecialistDmitry().getLastName())
            .description(EntityUtil.getSpecialistDmitry().getUserInfo().getDescription())
            .build();

    public final static UserCreateEditDto UPDATED_SPECIALIST_DTO = UserCreateEditDto.builder()
            .email("dimetros@gmail.com")
            .mobilePhoneNumber("+7(931)493-48-48")
            .role(Role.SPECIALIST)
            .password("9jie3e")
            .gender(Gender.MALE)
            .birthday(EntityUtil.getSpecialistDmitry().getUserInfo().getBirthday())
            .firstName(EntityUtil.getSpecialistDmitry().getFirstName())
            .lastName("Cheryomuhin")
            .description(EntityUtil.getSpecialistDmitry().getUserInfo().getDescription())
            .build();
}
