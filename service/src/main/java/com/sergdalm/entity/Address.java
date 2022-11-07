package com.sergdalm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"appointments", "serviceSales", "specialistAvailableTimes"})
@EqualsAndHashCode(of = "address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String addressName;

    @Column(nullable = false)
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<ServiceSale> serviceSales = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<SpecialistAvailableTime> specialistAvailableTimes = new ArrayList<>();
}
