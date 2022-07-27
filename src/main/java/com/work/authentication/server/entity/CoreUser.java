package com.work.authentication.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Users")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoreUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;
    @Column(name = "username")
    String username;
    @Column(name = "Hasheduserpassword")
    String hashedUserPassword;
    @Column(name = "Apppassword")
    String appPassword;
    @Column(name="staffid")
    String staffId;
    @OneToOne
    @JoinColumn(name = "Roleid")
    CoreRole role;
    @Column(name = "status")
    String status;
}
