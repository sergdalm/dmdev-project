package com.sergdalm.util;

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
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@UtilityClass
public class TestDataImporter {

    // These fields declared public in order to provide saved entity's id to test classes
    public User specialistDmitry;
    public User specialistNatali;
    public User administratorAlex;
    public User clientSvetlana;
    public User clientMaria;
    public User clientKirill;
    public User clientAnna;
    public User clientElena;
    public User clientTamara;
    public Address addressMalayMoyka;
    public Address addressStachek;
    public Service classicMassage;
    public Service cuppingMassage;
    public Service lymphaticDrainageMassage;
    public Service honeyMassage;

    public void importData(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            specialistDmitry = saveUser("Dmitriy", "Cheremuhin", "dshg3hd", "dmitry@gmail.com", Role.SPECIALIST, Gender.MALE, LocalDate.of(1977, 5, 29), session);
            specialistNatali = saveUser("Natali", "K", "d3892d", "natali@gmail.com", Role.SPECIALIST, Gender.FEMALE, LocalDate.of(1984, 3, 2), session);
            administratorAlex = saveUser("Alex", "S", "fifj43", "alex@gmail.com", Role.ADMINISTRATOR, Gender.ANOTHER, LocalDate.of(1993, 7, 12), session);
            clientSvetlana = saveUser("Svetlana", "V", "3932jrn", "svetlana@gmail.com", Role.CLIENT, Gender.FEMALE, LocalDate.of(1955, 4, 23), session);
            clientMaria = saveUser("Maria", "Ivanova", "0tkkd", "maria@gmail.com", Role.CLIENT, Gender.FEMALE, LocalDate.of(1990, 6, 6), session);
            clientKirill = saveUser("Kirill", "Petrov", "mvnuwe", "kirill@gmail.com", Role.CLIENT, Gender.MALE, LocalDate.of(1970, 3, 15), session);
            clientAnna = saveUser("Anna", "Kuzmina", "rfej3", "anna@gmail.com", Role.CLIENT, Gender.FEMALE, LocalDate.of(1996, 10, 20), session);
            clientElena = saveUser("Elena", "Pushkova", "dmf03j", "elena@gmail.com", Role.CLIENT, Gender.FEMALE, LocalDate.of(1994, 9, 1), session);
            clientTamara = saveUser("Tamara", "Shtuchkina", "dmsprj", "tamara@gmail.com", Role.CLIENT, Gender.FEMALE, LocalDate.of(1995, 8, 16), session);

            classicMassage = saveService(ServiceName.CLASSIC_MASSAGE, "Relaxing massage", session);
            cuppingMassage = saveService(ServiceName.CUPPING_MASSAGE, "Helps with pain, inflammation, blood flow, relaxation and well-being", session);
            lymphaticDrainageMassage = saveService(ServiceName.LYMPHATIC_DRAINAGE_MASSAGE, "Helps to lose weight", session);
            honeyMassage = saveService(ServiceName.HONEY_MASSAGE, "Improves blood circulation in deeper layers of the skin and warms and tones it", session);

            SpecialistService classicMassageDmitry = saveSpecialistService(specialistDmitry, classicMassage, 60, 1000, session);
            saveSpecialistService(specialistDmitry, cuppingMassage, 60, 1000, session);
            saveSpecialistService(specialistDmitry, lymphaticDrainageMassage, 60, 1000, session);
            saveSpecialistService(specialistDmitry, honeyMassage, 60, 1000, session);
            saveSpecialistService(specialistDmitry, classicMassage, 90, 1500, session);
            saveSpecialistService(specialistDmitry, cuppingMassage, 90, 1500, session);
            saveSpecialistService(specialistDmitry, lymphaticDrainageMassage, 90, 1500, session);
            saveSpecialistService(specialistDmitry, honeyMassage, 90, 1500, session);

            SpecialistService classicMassageNatali = saveSpecialistService(specialistNatali, classicMassage, 60, 2000, session);
            saveSpecialistService(specialistNatali, lymphaticDrainageMassage, 60, 2000, session);
            saveSpecialistService(specialistNatali, honeyMassage, 60, 2000, session);
            saveSpecialistService(specialistNatali, classicMassage, 90, 2500, session);
            saveSpecialistService(specialistNatali, lymphaticDrainageMassage, 90, 2500, session);
            saveSpecialistService(specialistNatali, honeyMassage, 90, 2500, session);

            addressMalayMoyka = saveAddress("Malaya Morskaya 16", "Go out Admiralteyskaya subway station and go to the right", session);
            addressStachek = saveAddress("Ploshad Stachek 9", "Go out Narvsaky subway station and cross the street", session);

            saveSpecialistAvailableTime(specialistDmitry, addressMalayMoyka, LocalDate.of(2022, 10, 15), LocalTime.of(12, 0), session);
            saveSpecialistAvailableTime(specialistDmitry, addressMalayMoyka, LocalDate.of(2022, 10, 15), LocalTime.of(13, 0), session);
            saveSpecialistAvailableTime(specialistDmitry, addressMalayMoyka, LocalDate.of(2022, 10, 15), LocalTime.of(14, 0), session);
            saveSpecialistAvailableTime(specialistDmitry, addressMalayMoyka, LocalDate.of(2022, 10, 15), LocalTime.of(15, 0), session);
            saveSpecialistAvailableTime(specialistDmitry, addressMalayMoyka, LocalDate.of(2022, 10, 15), LocalTime.of(16, 0), session);
            saveSpecialistAvailableTime(specialistNatali, addressMalayMoyka, LocalDate.of(2022, 10, 15), LocalTime.of(17, 0), session);
            saveSpecialistAvailableTime(specialistNatali, addressMalayMoyka, LocalDate.of(2022, 10, 15), LocalTime.of(18, 0), session);
            saveSpecialistAvailableTime(specialistNatali, addressMalayMoyka, LocalDate.of(2022, 10, 15), LocalTime.of(19, 0), session);
            saveSpecialistAvailableTime(specialistNatali, addressMalayMoyka, LocalDate.of(2022, 10, 15), LocalTime.of(20, 0), session);

            saveSpecialistAvailableTime(specialistDmitry, addressStachek, LocalDate.of(2022, 10, 16), LocalTime.of(12, 0), session);
            saveSpecialistAvailableTime(specialistDmitry, addressStachek, LocalDate.of(2022, 10, 16), LocalTime.of(13, 0), session);
            saveSpecialistAvailableTime(specialistDmitry, addressStachek, LocalDate.of(2022, 10, 16), LocalTime.of(14, 0), session);
            saveSpecialistAvailableTime(specialistDmitry, addressStachek, LocalDate.of(2022, 10, 16), LocalTime.of(15, 0), session);
            saveSpecialistAvailableTime(specialistDmitry, addressStachek, LocalDate.of(2022, 10, 16), LocalTime.of(16, 0), session);
            saveSpecialistAvailableTime(specialistNatali, addressStachek, LocalDate.of(2022, 10, 16), LocalTime.of(17, 0), session);
            saveSpecialistAvailableTime(specialistNatali, addressStachek, LocalDate.of(2022, 10, 16), LocalTime.of(18, 0), session);
            saveSpecialistAvailableTime(specialistNatali, addressStachek, LocalDate.of(2022, 10, 16), LocalTime.of(19, 0), session);
            saveSpecialistAvailableTime(specialistNatali, addressStachek, LocalDate.of(2022, 10, 16), LocalTime.of(20, 0), session);

            saveAppointment(clientAnna, specialistDmitry, addressMalayMoyka, honeyMassage, LocalDate.of(2022, 10, 3), LocalTime.of(12, 0), 60, 1000, AppointmentStatus.COMPLETED_PAID, session);
            saveAppointment(clientElena, specialistDmitry, addressMalayMoyka, classicMassage, LocalDate.of(2022, 10, 3), LocalTime.of(13, 0), 60, 1000, AppointmentStatus.COMPLETED_NOT_PAID, session);
            saveAppointment(clientKirill, specialistDmitry, addressMalayMoyka, cuppingMassage, LocalDate.of(2022, 10, 3), LocalTime.of(14, 0), 60, 1000, AppointmentStatus.CANCELED, session);
            saveAppointment(clientMaria, specialistDmitry, addressMalayMoyka, classicMassage, LocalDate.of(2022, 10, 3), LocalTime.of(15, 0), 60, 1000, AppointmentStatus.COMPLETED_PAID, session);
            saveAppointment(clientTamara, specialistDmitry, addressMalayMoyka, classicMassage, LocalDate.of(2022, 10, 3), LocalTime.of(16, 0), 60, 1000, AppointmentStatus.COMPLETED_NOT_PAID, session);
            saveAppointment(clientSvetlana, specialistDmitry, addressMalayMoyka, lymphaticDrainageMassage, LocalDate.of(2022, 10, 3), LocalTime.of(17, 0), 60, 1000, AppointmentStatus.CANCELED, session);
            saveAppointment(clientSvetlana, specialistDmitry, addressMalayMoyka, honeyMassage, LocalDate.of(2022, 10, 15), LocalTime.of(17, 0), 60, 1000, AppointmentStatus.CONFIRMED_AND_SCHEDULED, session);

            saveAppointment(clientMaria, specialistNatali, addressStachek, classicMassage, LocalDate.of(2022, 10, 5), LocalTime.of(12, 0), 60, 1000, AppointmentStatus.COMPLETED_PAID, session);
            saveAppointment(clientTamara, specialistNatali, addressStachek, classicMassage, LocalDate.of(2022, 10, 5), LocalTime.of(13, 0), 60, 1000, AppointmentStatus.COMPLETED_NOT_PAID, session);
            saveAppointment(clientAnna, specialistNatali, addressStachek, honeyMassage, LocalDate.of(2022, 10, 5), LocalTime.of(14, 0), 60, 1000, AppointmentStatus.CANCELED, session);
            saveAppointment(clientKirill, specialistNatali, addressStachek, classicMassage, LocalDate.of(2022, 10, 5), LocalTime.of(15, 0), 60, 1000, AppointmentStatus.COMPLETED_PAID, session);
            saveAppointment(clientElena, specialistNatali, addressStachek, classicMassage, LocalDate.of(2022, 10, 5), LocalTime.of(16, 0), 60, 1000, AppointmentStatus.COMPLETED_NOT_PAID, session);
            saveAppointment(clientElena, specialistNatali, addressStachek, lymphaticDrainageMassage, LocalDate.of(2022, 10, 5), LocalTime.of(16, 0), 60, 1000, AppointmentStatus.COMPLETED_NOT_PAID, session);
            saveAppointment(clientSvetlana, specialistNatali, addressStachek, classicMassage, LocalDate.of(2022, 10, 7), LocalTime.of(17, 0), 60, 1000, AppointmentStatus.CANCELED, session);
            saveAppointment(clientSvetlana, specialistNatali, addressStachek, classicMassage, LocalDate.of(2022, 10, 11), LocalTime.of(17, 0), 60, 1000, AppointmentStatus.CONFIRMED_AND_SCHEDULED, session);

            saveReview(specialistDmitry, clientSvetlana, "Dmitry is ver good specialist", LocalDateTime.now(), session);
            saveReview(specialistDmitry, clientTamara, "Good", LocalDateTime.now(), session);

            saveServiceSale(LocalDate.of(2022, 10, 20), 14, 600, classicMassageDmitry, addressStachek, session);
            saveServiceSale(LocalDate.of(2022, 10, 20), 14, 900, classicMassageNatali, addressStachek, session);
        }
    }

    private User saveUser(String firstName,
                          String lastName,
                          String password,
                          String email,
                          Role role,
                          Gender gender,
                          LocalDate birthday,
                          Session session) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(role)
                .password(password)
                .gender(gender)
                .birthday(birthday)
                .build();
        session.save(user);

        return user;
    }

    private Service saveService(ServiceName name,
                                String description,
                                Session session) {
        Service service = Service.builder()
                .name(name)
                .description(description)
                .build();
        session.save(service);

        return service;
    }

    private SpecialistService saveSpecialistService(User specialist,
                                                    Service service,
                                                    Integer lengthMin,
                                                    Integer price,
                                                    Session session) {
        SpecialistService specialistService = SpecialistService.builder()
                .lengthMin(lengthMin)
                .price(price)
                .build();
        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        session.save(specialistService);

        return specialistService;
    }

    private Address saveAddress(String addressField,
                                String description,
                                Session session) {
        Address address = Address.builder()
                .addressName(addressField)
                .description(description)
                .build();
        session.save(address);

        return address;
    }

    private SpecialistAvailableTime saveSpecialistAvailableTime(User specialist,
                                                                Address address,
                                                                LocalDate date,
                                                                LocalTime time,
                                                                Session session) {
        SpecialistAvailableTime specialistAvailableTime =
                SpecialistAvailableTime.builder()
                        .date(date)
                        .time(time)
                        .build();
        specialistAvailableTime.setSpecialist(specialist);
        specialistAvailableTime.setAddress(address);
        session.save(specialistAvailableTime);

        return specialistAvailableTime;
    }

    private Appointment saveAppointment(User client,
                                        User specialist,
                                        Address address,
                                        Service service,
                                        LocalDate date,
                                        LocalTime time,
                                        Integer lengthMin,
                                        Integer price,
                                        AppointmentStatus status,
                                        Session session) {
        Appointment appointment = Appointment.builder()
                .date(date)
                .startTime(time)
                .lengthMin(lengthMin)
                .price(price)
                .status(status)
                .build();
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setService(service);
        appointment.setAddress(address);
        session.save(appointment);

        return appointment;
    }

    private Review saveReview(User specialist,
                              User client,
                              String content,
                              LocalDateTime publishedAt,
                              Session session) {
        Review review = Review.builder()
                .content(content)
                .publishedAt(publishedAt)
                .build();
        review.setSpecialist(specialist);
        review.setClient(client);
        session.save(review);
        return review;
    }

    private ServiceSale saveServiceSale(LocalDate startDate,
                                        Integer durationDays,
                                        Integer salePrice,
                                        SpecialistService specialistService,
                                        Address address,
                                        Session session) {
        ServiceSale serviceSale = ServiceSale.builder()
                .startDate(startDate)
                .durationDays(durationDays)
                .salePrice(salePrice)
                .build();
        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        session.save(serviceSale);
        return serviceSale;

    }
}
