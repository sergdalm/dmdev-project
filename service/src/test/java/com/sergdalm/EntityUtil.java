package com.sergdalm;

import com.sergdalm.entity.Address;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.DateAndTime;
import com.sergdalm.entity.Gender;
import com.sergdalm.entity.Review;
import com.sergdalm.entity.Role;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.ServiceSale;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import com.sergdalm.entity.UserInfo;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class EntityUtil {

    public static User getUserAdministrator() {
        return User.builder()
                .email("admin@mail.com")
                .password("1djh2l")
                .mobilePhoneNumber("+7(911)123-45-67")
                .role(Role.ADMINISTRATOR)
                .build();
    }

    public static UserInfo getAdministratorUserInfo() {
        return UserInfo.builder()
                .firstName("Alex")
                .lastName("S")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1993, 7, 12))
                .gender(Gender.FEMALE)
                .registeredAt(LocalDateTime.now())
                .build();
    }

    public static User getUserClient() {
        return User.builder()
                .email("client@mail.com")
                .mobilePhoneNumber("+7(911)545-78-22")
                .password("dn38d")
                .role(Role.CLIENT)
                .build();
    }

    public static UserInfo getClientUserInfo() {
        return UserInfo.builder()

                .firstName("Svetlana")
                .lastName("Petrova")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1985, 3, 1))
                .gender(Gender.FEMALE)
                .registeredAt(LocalDateTime.now())
                .build();
    }

    public static User getUserSpecialist() {
        return User.builder()
                .email("dmitry@mail.com")
                .mobilePhoneNumber("+7(911)222-34-55")
                .password("39239")
                .role(Role.SPECIALIST)
                .build();
    }

    public static UserInfo getSpecialistUserInfo() {
        return UserInfo.builder()
                .firstName("Dmitry")
                .lastName("Cheremuhin")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1977, 5, 25))
                .gender(Gender.FEMALE)
                .description("description")
                .registeredAt(LocalDateTime.now())
                .build();
    }

    public static Appointment getAppointment() {
        return Appointment.builder()
                .dateAndTime(new DateAndTime(
                        LocalDateTime.of(2022, 10, 20, 12, 0)))
                .lengthMin(90)
                .price(2000)
                .status(AppointmentStatus.CREATED_NOT_CONFIRMED)
                .build();
    }

    public static Address getAddress() {
        return Address.builder()
                .addressName("Nevsky pr. 16, 5")
                .description("Nearby subway Gostinny drov")
                .build();
    }

    public static Review getReview() {
        return Review.builder()
                .publishedAt(LocalDateTime.now())
                .content("Good!!")
                .build();
    }

    public static Service getService() {
        return Service.builder()
                .name(ServiceName.ACUPRESSURE)
                .description("Good for health")
                .build();
    }

    public static ServiceSale getServiceSale() {
        return ServiceSale.builder()
                .startDate(LocalDate.of(2022, 10, 10))
                .durationDays(10)
                .salePrice(1000)
                .build();
    }

    public static SpecialistAvailableTime getSpecialistAvailableTime() {
        return SpecialistAvailableTime.builder()
                .dateAndTime(new DateAndTime(
                        LocalDateTime.of(2022, 10, 15, 12, 0)))
                .build();
    }

    public static SpecialistService getSpecialistService() {
        return SpecialistService.builder()
                .lengthMin(90)
                .price(2000)
                .build();
    }
}
