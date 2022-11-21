package com.sergdalm.dao.filter;

import com.sergdalm.entity.Gender;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserFilter {
    String email;
    String mobilePhoneNumber;
    String firstName;
    String lastName;
    Gender gender;
    LocalDate birthdayAfterDate;
    LocalDate birthdayBeforeDate;
    LocalDate registeredAfterDate;
    LocalDate registeredBeforeDate;
    boolean hasDescription;
}
