package com.sergdalm.entity;

import com.sergdalm.dao.DateAndTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@ToString(exclude = {"client", "specialist", "address", "service", "transaction"})
@EqualsAndHashCode(exclude = {"client", "specialist", "address", "service", "transaction"})
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

    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "start_time"))
    private DateAndTime dateAndTime;

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
