package com.sergdalm.entity;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"appointments", "specialistServices"}, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("specialist")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Specialist extends User {

    @Type(type = "jsonb")
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "specialist")
    private List<Appointment> appointments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "specialist")
    private List<SpecialistService> specialistServices = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "specialist")
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "specialist")
    private List<SpecialistAvailableTime> specialistAvailableTimes = new ArrayList<>();
}
