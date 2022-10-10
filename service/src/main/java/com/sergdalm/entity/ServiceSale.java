package com.sergdalm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@ToString(exclude = {"address", "specialistService"})
@EqualsAndHashCode(exclude = {"address", "specialistService"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ServiceSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SpecialistService specialistService;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Address address;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private Integer durationDays;

    @Column(nullable = false)
    private Integer salePrice;

    public void setSpecialistService(SpecialistService specialistService) {
        this.specialistService = specialistService;
        specialistService.getSales().add(this);
    }

    public void setAddress(Address address) {
        this.address = address;
        address.getServiceSales().add(this);
    }
}
