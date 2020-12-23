//package com.uni.common.springboot.entity;
//
//import com.uni.common.springboot.core.Context;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.Column;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.PrePersist;
//import javax.persistence.PreUpdate;
//import java.time.LocalDateTime;
//
//@Setter
//@Getter
//@MappedSuperclass
//public abstract class UserTimeEntity {
//    @Column(name = "insert_user", length = DataTypeLength.ID, nullable = false)
//    private String insertUser;
//
//    @Column(name = "insert_date_time", columnDefinition = DbDataType.TIMESTAMP, nullable = false)
//    private LocalDateTime insertDateTime;
//
//    @Column(name = "update_user", length = DataTypeLength.ID)
//    private String updateUser;
//
//    @Column(name = "update_date_time", columnDefinition = DbDataType.TIMESTAMP)
//    private LocalDateTime updateDateTime;
//
//    @PrePersist
//    private void assignInsertUserTime() {
//        insertUser = Context.getContext().getUserUid();
//        insertDateTime = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    private void assignUpdateUserTime() {
//        updateUser = Context.getContext().getUserUid();
//        updateDateTime = LocalDateTime.now();
//    }
//}
