package com.carbon.hospitalitysystem.entities;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import javax.persistence.*;


@Data
@ToString
@MappedSuperclass
public abstract class AbstractBaseEntity<T> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private T id;

    @CreatedDate
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime dateModified;


}
