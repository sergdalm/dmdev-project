package com.sergdalm.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DateAndTime {

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    public DateAndTime(LocalDateTime localDateTime) {
        this.date = localDateTime.toLocalDate();
        this.time = localDateTime.toLocalTime();
    }
}
