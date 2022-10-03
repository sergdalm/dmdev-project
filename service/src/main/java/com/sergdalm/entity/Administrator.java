package com.sergdalm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("administrator")
public class Administrator extends User {
}
