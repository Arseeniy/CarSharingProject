package com.arseeniy.mycarsharing.common.entity.authorization;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    @OneToOne(mappedBy = "role")
    private User user;

    public Role(ERole name) {
        this.name = name;
    }
}
