package com.sergdalm.dao.filter;

import com.sergdalm.entity.Address;
import com.sergdalm.entity.Gender;
import com.sergdalm.entity.ServiceName;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Value
@Builder
public class SpecialistFilter {
    String email;
    String mobilePhoneNumber;
    String firstName;
    String lastName;
    Gender gender;
    LocalDate birthdayAfterDate;
    LocalDate birthdayBeforeDate;
    LocalDate registeredBeforeDate;
    LocalDate registeredAfterDate;
    boolean hasDescription;
    Boolean hasReviews;
    List<ServiceName> serviceNames;
    List<Address> addressWhereHaveAppointments;
    List<LocalDate> availableDates;
    List<LocalTime> availableTimes;
    List<Address> availableAddresses;
}
