package com.work.authentication.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Permission")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CorePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;
    @Column(name = "name")
    String permission;
    @Column(name = "description")
    String description;
    @Column(name = "createddt")
    Date createdDt;
    @Column(name = "createdBy")
    String createdBy;
}
