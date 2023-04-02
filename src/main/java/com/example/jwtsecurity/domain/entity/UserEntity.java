package com.example.jwtsecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author joetao
 */
@Entity
@Table(name="sys_user", schema = "index_manager", uniqueConstraints = @UniqueConstraint(columnNames= {"username"}))
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Data
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false,columnDefinition = "bigint unsigned not null comment '自增id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号'")
    private String username;
    @Column(name = "nickname", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称'")
    private String nickname;
    @Column(name = "password", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码'")
    private String password;
    @Column(name = "state", columnDefinition = "int unsigned DEFAULT 1 COMMENT '启用状态，1：启用，0：停用'")
    private Integer state;
    @Column(name = "is_dark", columnDefinition = "int unsigned DEFAULT 1 COMMENT '是否启用暗黑模式，1：启用，0：停用'")
    private Integer isDark;

    @Basic
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    @CreatedDate
    private Timestamp createTime;
    @Basic
    @Column(name = "update_time", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'")
    @LastModifiedDate
    private Timestamp updateTime;
}
