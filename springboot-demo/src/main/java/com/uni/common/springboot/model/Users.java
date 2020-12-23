//package com.uni.common.springboot.model;
//
//import com.uni.common.springboot.entity.DataTypeLength;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Setter
//@Getter
//@Table(name = "users",uniqueConstraints =
//        {@UniqueConstraint(columnNames = {"username"})})
//public class Users {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "username", length = DataTypeLength.ID, nullable = false)
//    private String username;
//
//    @Column(name = "password", length = DataTypeLength.ID, nullable = false)
//    private String password;
//
//    @Column(name = "name", length = DataTypeLength.ID, nullable = false)
//    private String name;
//}
