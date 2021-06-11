package com.carbon.hospitalitysystem.entities;

import com.carbon.hospitalitysystem.enums.CutomerType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Data
@Builder
@Table(name = "customer_table")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity extends AbstractBaseEntity<Long>{

    private String name;
    @Enumerated(EnumType.STRING)
    private CutomerType customerType;

}
