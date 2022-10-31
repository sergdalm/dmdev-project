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
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@ToString(exclude = {"specialist", "client"})
@EqualsAndHashCode(exclude = {"id", "specialist", "client"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "massage")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User specialist;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User client;

    @Column(nullable = false)
    private LocalDateTime publishedAt;

    @Column(nullable = false)
    private String content;

    public void setSpecialist(User specialist) {
        this.specialist = specialist;
        specialist.getSpecialistReviews().add(this);
    }

    public void setClient(User client) {
        this.client = client;
        client.getClientReviews().add(this);
    }
}
