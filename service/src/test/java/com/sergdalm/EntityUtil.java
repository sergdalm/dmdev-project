package com.sergdalm;

import com.sergdalm.entity.Address;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
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
import java.time.LocalTime;

@UtilityClass
public class EntityUtil {

    public static User getAdministrator() {
        return User.builder()
                .id(3)
                .email("alex@gmail.com")
                .password("fj04ff")
                .mobilePhoneNumber("+7(911)332-65-23")
                .role(Role.ADMINISTRATOR)
                .build();
    }

    public static UserInfo getAdministratorUserInfo() {
        return UserInfo.builder()
                .id(3)
                .firstName("Alex")
                .lastName("S")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1993, 7, 12))
                .gender(Gender.FEMALE)
                .registeredAt(LocalDateTime.of(2022, 11, 13, 15, 24))
                .build();
    }

    public static User getClientSvetlana() {
        return User.builder()
                .id(4)
                .email("svetlana@gmail.com")
                .mobilePhoneNumber("+7(911)849-93-13")
                .password("28ff")
                .role(Role.CLIENT)
                .build();
    }

    public static UserInfo getClientSvetlanaUserInfo() {
        return UserInfo.builder()
                .id(4)
                .firstName("Svetlana")
                .lastName("Cheremuhina")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1955, 4, 24))
                .registeredAt(LocalDateTime.of(2022, 11, 14, 15, 24))
                .build();
    }

    public static User getClientMarina() {
        return User.builder()
                .id(5)
                .email("marina@gmail.com")
                .mobilePhoneNumber("+7(911)492-06-02")
                .role(Role.CLIENT)
                .password("23d0i9")
                .userInfo(getClientMarinaUserInfo())
                .build();
    }

    public static UserInfo getClientMarinaUserInfo() {
        return UserInfo.builder()
                .id(5)
                .firstName("Marina")
                .lastName("Ivanova")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1990, 7, 20))
                .registeredAt(LocalDateTime.of(2022, 11, 15, 13, 0))
                .build();
    }

    public static User getClientKatya() {
        return User.builder()
                .id(6)
                .email("marina@gmail.com")
                .mobilePhoneNumber("+7(911)214-05-91")
                .role(Role.CLIENT)
                .password("wc09jn")
                .build();
    }

    public static UserInfo getClientKatyaUserInfo() {
        return UserInfo.builder()
                .id(6)
                .firstName("Katya")
                .lastName("Petrova")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1995, 9, 1))
                .registeredAt(LocalDateTime.of(2022, 11, 16, 13, 15))
                .build();
    }

    public static User getSpecialistDmitry() {
        return User.builder()
                .id(1)
                .email("dmitry@gmail.com")
                .mobilePhoneNumber("+7(911)749-94-28")
                .password("6403uh")
                .role(Role.SPECIALIST)
                .build();
    }

    public static UserInfo getSpecialistDmitryUserInfo() {
        return UserInfo.builder()
                .id(1)
                .firstName("Dmitry")
                .lastName("Cheremuhin")
                .gender(Gender.MALE)
                .birthday(LocalDate.of(1975, 5, 25))
                .description("Work experience: 3 years")
                .registeredAt(LocalDateTime.of(2022, 11, 14, 13, 23))
                .build();
    }

    public static User getSpecialistNatali() {
        return User.builder()
                .id(2)
                .email("natali@gmail.com")
                .mobilePhoneNumber("+7(911)493-09-02")
                .password("093jrnd")
                .role(Role.SPECIALIST)
                .build();
    }

    public static UserInfo getSpecialistNataliUserInfo() {
        return UserInfo.builder()
                .id(2)
                .firstName("Natali")
                .lastName("Kremneva")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1983, 4, 29))
                .description("Work experience: 10 years")
                .registeredAt(LocalDateTime.of(2022, 11, 14, 15, 24))
                .build();
    }

    public static Appointment getAppointment() {
        return Appointment.builder()
                .date(LocalDate.of(2022, 10, 20))
                .startTime(LocalTime.of(12, 0))
                .lengthMin(60)
                .price(1000)
                .status(AppointmentStatus.CREATED_NOT_CONFIRMED)
                .address(getAddressNarvskaya())
                .client(getClientSvetlana())
                .specialist(getSpecialistDmitry())
                .service(getServiceClassicMassage())
                .build();
    }

    public static Address getAddressNarvskaya() {
        return Address.builder()
                .id(1)
                .addressName("Narvskaya")
                .description("Admiralteyskaya', 'Malaya Moraskaya, 16")
                .build();
    }

    public static Address getAddressAdmiralteyskaya() {
        return Address.builder()
                .id(1)
                .addressName("Admiralteyskaya")
                .description("Admiralteyskaya', 'Prospect stacheck, 50")
                .build();
    }

    public static Review getReview() {
        return Review.builder()
                .id(1)
                .client(getClientSvetlana())
                .specialist(getSpecialistDmitry())
                .publishedAt(LocalDateTime.now())
                .content("Good!!")
                .build();
    }

    public static Service getServiceClassicMassage() {
        return Service.builder()
                .name(ServiceName.CLASSIC_MASSAGE)
                .description("Relaxing massage")
                .build();
    }

    public static Service getServiceHoneyMassage() {
        return Service.builder()
                .name(ServiceName.HONEY_MASSAGE)
                .description("Good for health")
                .build();
    }

    public static Service getServiceCuppingMassage() {
        return Service.builder()
                .name(ServiceName.CUPPING_MASSAGE)
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
                .date(LocalDate.of(2022, 10, 15))
                .time(LocalTime.of(12, 0))
                .build();
    }

    public static SpecialistService getSpecialistService() {
        return SpecialistService.builder()
                .lengthMin(90)
                .price(2000)
                .build();
    }
}
