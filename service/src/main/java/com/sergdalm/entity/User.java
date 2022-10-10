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
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"account", "specialistReviews",
        "specialistAppointments", "clientAppointments",
        "specialistServices", "specialistAvailableTimes",
        "clientReviews", "specialistReviews"})
@EqualsAndHashCode(of = "email")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(unique = true)
    private String mobilePhoneNumber;

    private String description;

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
