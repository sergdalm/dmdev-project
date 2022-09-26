package com.sergdalm;

import com.sergdalm.entity.Administrator;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.Client;
import com.sergdalm.entity.Gender;
import com.sergdalm.entity.MassageType;
import com.sergdalm.entity.Specialist;
import com.sergdalm.entity.UserInformation;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class EntityUtil {

    public static Administrator getAdministrator() {
        return Administrator.builder()
                .userInformation(UserInformation.builder()
                        .email("admin@mail.com")
                        .firstName("Alex")
                        .lastName("S")
                        .birthday(LocalDate.of(1993, 7, 12))
                        .gender(Gender.FEMALE)
                        .build())
                .build();
    }

    public static Client getClient() {
        return Client.builder()
                .userInformation(UserInformation.builder()
                        .email("client@mail.com")
                        .firstName("Svetlana")
                        .lastName("Petrova")
                        .birthday(LocalDate.of(1985, 3, 1))
                        .gender(Gender.FEMALE)
                        .build())
                .build();
    }


    public static Specialist getSpecialist() {
        return Specialist.builder()
                .userInformation(UserInformation.builder()
                        .email("dima@mail.com")
                        .firstName("Dmitri")
                        .lastName("Cheremuhin")
                        .birthday(LocalDate.of(1977, 5, 25))
                        .gender(Gender.FEMALE)
                        .build())
                .build();
    }

    public static Appointment getAppointment() {
        return Appointment.builder()
                .clientId(1)
                .specialistId(1)
                .date(LocalDate.of(2022, 9, 30))
                .startTime(LocalTime.of(11, 0))
                .lengthMin(90)
                .massageType(MassageType.CLASSIC)
                .build();
    }
}
