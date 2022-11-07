package com.sergdalm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@ToString(exclude = {"specialist", "address"})
@EqualsAndHashCode(exclude = {"id", "specialist", "address"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SpecialistAvailableTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User specialist;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Address address;

    @Embedded
    private DateAndTime dateAndTime;

    public void setSpecialist(User specialist) {
        this.specialist = specialist;
        specialist.getSpecialistAvailableTimes().add(this);
    }

    public void setAddress(Address address) {
        this.address = address;
        address.getSpecialistAvailableTimes().add(this);
    }
}
