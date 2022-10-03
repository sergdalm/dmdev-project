package com.sergdalm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@ToString(exclude = {"specialist", "address"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SpecialistAvailableTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Specialist specialist;

    @ManyToOne(optional = false)
    private Address address;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
        specialist.getSpecialistAvailableTimes().add(this);
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
