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
@ToString(exclude = {"client", "specialist", "address", "service"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Client client;

    @ManyToOne(optional = false)
    private Specialist specialist;

    @ManyToOne(optional = false)
    private Address address;

    @ManyToOne(optional = false)
    private Service service;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private Integer lengthMin;

    public void setClient(Client client) {
        this.client = client;
        client.getAppointments().add(this);
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
        specialist.getAppointments().add(this);
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
