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
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@UtilityClass
public class EntityUtil {

    public static User getUserAdministrator() {
        return User.builder()
                .email("admin@mail.com")
                .password("1djh2l")
                .firstName("Alex")
                .lastName("S")
                .gender(Gender.FEMALE)
                .role(Role.ADMINISTRATOR)
                .birthday(LocalDate.of(1993, 7, 12))
                .mobilePhoneNumber("+7(911)123-45-67")
                .gender(Gender.FEMALE)
                .build();
    }

    public static User getUserClient() {
        return User.builder()
                .email("client@mail.com")
                .role(Role.CLIENT)
                .password("dn38d")
                .firstName("Svetlana")
                .lastName("Petrova")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1985, 3, 1))
                .mobilePhoneNumber("+7(911)545-78-22")
                .birthday(LocalDate.of(1993, 7, 12))
                .gender(Gender.FEMALE)
                .build();
    }

    public static User getUserSpecialist() {
        return User.builder()
                .email("dmitry@mail.com")
                .password("39239")
                .role(Role.SPECIALIST)
                .firstName("Dmitry")
                .lastName("Cheremuhin")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1977, 5, 25))
                .mobilePhoneNumber("+7(911)222-34-55")
                .gender(Gender.FEMALE)
                .description("""
                        {
                        "description": "description",
                        "experience (years)": 6
                        }
                        """)
                .build();
    }

    public static Appointment getAppointment() {
        return Appointment.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(12, 0))
                .lengthMin(90)
                .price(2000)
                .status(AppointmentStatus.CREATED_NOT_CONFIRMED)
                .build();
    }

    public static Address getAddress() {
        return Address.builder()
                .address("Nevsky pr. 16, 5")
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
                .name(ServiceName.CLASSIC_MASSAGE)
                .description("Classic massage is relaxing")
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
