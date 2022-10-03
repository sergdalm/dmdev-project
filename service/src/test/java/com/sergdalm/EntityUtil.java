package com.sergdalm;


import com.sergdalm.entity.Account;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Administrator;
import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.Client;
import com.sergdalm.entity.Gender;
import com.sergdalm.entity.MobilePhoneNumber;
import com.sergdalm.entity.Review;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.ServiceSale;
import com.sergdalm.entity.Specialist;
import com.sergdalm.entity.SpecialistAvailableTime;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.Transaction;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@UtilityClass
public class EntityUtil {

    public static Administrator getAdministrator() {
        return Administrator.builder()
                .email("admin@mail.com")
                .password("1djh2l")
                .firstName("Alex")
                .lastName("S")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1993, 7, 12))
                .mobilePhone(new MobilePhoneNumber("911", "123", "45", "67"))
                .gender(Gender.FEMALE)
                .build();
    }

    public static Client getClient() {
        return Client.builder()
                .email("client@mail.com")
                .password("dn38d")
                .firstName("Svetlana")
                .lastName("Petrova")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1985, 3, 1))
                .mobilePhone(new MobilePhoneNumber("911", "242", "88", "44"))
                .birthday(LocalDate.of(1993, 7, 12))
                .gender(Gender.FEMALE)
                .build();
    }

    public static Specialist getSpecialist() {
        return Specialist.builder()
                .email("dmitry@mail.com")
                .password("39239")
                .firstName("Dmitry")
                .lastName("Cheremuhin")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(1977, 5, 25))
                .mobilePhone(new MobilePhoneNumber("911", "039", "40", "20"))
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
                .build();
    }

    public static Address getAddress() {
        return Address.builder()
                .address("Nevsky pr. 16, 5")
                .description("Nearby subway Gostinny drov")
                .build();
    }

    public static Account getAccountWithZeroAmount() {
        return Account.builder()
                .currentAmount(0)
                .bankAccountInfo("""
                        {
                        "cartNumber": "000000000",
                        "bank": "Tinkoff"
                        }
                        """)
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

    public static Transaction getTransaction() {
        return Transaction.builder()
                .transferAmount(3000)
                .transferredAt(LocalDateTime.now())
                .build();
    }
}
