package com.springboot.blog.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),@UniqueConstraint(columnNames = {"email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name="username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    //Over here we will not fetch the role on the basis of demand whenever any user is fetched always the roles will be fetched
    //Over here you are creating a new table named as user_roles and over here you are referencing to the id which is primary key in User table but will be foreign key in the newly created table

    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
    private Set<Role> roles;
}
