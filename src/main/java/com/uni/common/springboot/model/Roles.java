package com.uni.common.springboot.model;

import com.uni.common.springboot.entity.DataTypeLength;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = DataTypeLength.ID, nullable = false)
    private String name;

}
