package com.sergdalm.filter;

import com.sergdalm.entity.Address;
import com.sergdalm.entity.ServiceName;
import com.sergdalm.entity.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Value
@Builder
public class SpecialistAvailableTimeFilter {
    Integer specialistId;
    Integer addressId;
    List<LocalDate> dates;
    List<LocalTime> times;

}
