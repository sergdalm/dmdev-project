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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"account", "specialistReviews",
        "specialistAppointments", "clientAppointments",
        "specialistServices", "specialistAvailableTimes",
        "clientReviews", "specialistReviews", "userInfo"})
@EqualsAndHashCode(of = "email")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "massage")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobilePhoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @Builder.Default
    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    private List<Review> specialistReviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Review> clientReviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    private List<Appointment> specialistAppointments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Appointment> clientAppointments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    private List<SpecialistService> specialistServices = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    private List<SpecialistAvailableTime> specialistAvailableTimes = new ArrayList<>();
}
