package com.example.jwtsecurity.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author joetao
 */
@Entity
@Table(name="sys_user_role")
@Data
public class UserRoleEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "role_id")
    private Long roleId;
}
