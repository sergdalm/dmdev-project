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

@Data
@ToString(exclude = {"specialist", "service"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SpecialistService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Specialist specialist;

    @ManyToOne(optional = false)
    private Service service;

    @Column(nullable = false)
    private Integer lengthMin;

    @Column(nullable = false)
    private Integer price;

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
        specialist.getSpecialistServices().add(this);
    }

    public void setService(Service service) {
        this.service = service;
    }
}
