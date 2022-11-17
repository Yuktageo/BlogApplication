package com.springboot.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.security.PrivateKey;

@Data
@Entity
@Table(name = "Role",uniqueConstraints = {@UniqueConstraint(columnNames = "roleName")})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    private String roleName;
}
