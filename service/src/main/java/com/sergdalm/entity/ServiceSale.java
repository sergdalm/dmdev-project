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
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@ToString(exclude = {"address", "specialistService"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ServiceSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false)
    private SpecialistService specialistService;

    @ManyToOne(optional = false)
    private Address address;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private Integer durationDays;

    @Column(nullable = false)
    private Integer salePrice;

    public void setSpecialistService(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
