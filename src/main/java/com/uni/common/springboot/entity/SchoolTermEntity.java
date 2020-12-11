package com.uni.common.springboot.entity;

import com.uni.common.springboot.core.Context;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@Setter
@Getter
@MappedSuperclass
public abstract class SchoolTermEntity extends OrganizationEntity {
    @Column(name = "school_term_uid", length = DataTypeLength.ID, nullable = false)
    private String schoolTermUid;

    @PrePersist
    private void assignSchoolTerm() {
        if (schoolTermUid == null)
            schoolTermUid = Context.getContext().getSchoolTermUid();
    }
}
