package com.work.authentication.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Table(name = "RolesPermission")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoreRolesPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;
    @Column(name = "rolesid")
    Long rolesId;
    @Column(name = "permissionid")
    Long permissionId;
}
