package com.sergdalm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@ToString(exclude = {"client", "specialist", "address", "service"})
@EqualsAndHashCode(exclude = {"id", "client", "specialist", "address", "service"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User client;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User specialist;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Address address;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Service service;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private Integer lengthMin;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    public void setClient(User client) {
        this.client = client;
        client.getClientAppointments().add(this);
    }

    public void setSpecialist(User specialist) {
        this.specialist = specialist;
        specialist.getClientAppointments().add(this);
    }

    public void setAddress(Address address) {
        this.address = address;
        address.getAppointments().add(this);
    }

    public void setService(Service service) {
        this.service = service;
        service.getAppointments().add(this);
    }
}
