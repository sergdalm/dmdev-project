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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"specialist", "service", "sales"})
@EqualsAndHashCode(exclude = {"id", "specialist", "service", "sales"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "massage")
public class SpecialistService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User specialist;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Service service;

    @Column(nullable = false)
    private Integer lengthMin;

    @Column(nullable = false)
    private Integer price;

    @Builder.Default
    @OneToMany(mappedBy = "specialistService", cascade = CascadeType.ALL)
    private List<ServiceSale> sales = new ArrayList<>();

    public void setSpecialist(User specialist) {
        this.specialist = specialist;
        specialist.getSpecialistServices().add(this);
    }

    public void setService(Service service) {
        this.service = service;
        service.getSpecialistServices().add(this);
    }
}
